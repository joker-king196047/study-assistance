import json
import requests
import html
import random
from datetime import datetime

API_URL = "https://opentdb.com/api.php"
PARAMS = {
    "amount": 50,
    "category": 18
}

def fetch_questions():
    print("正在从 Open Trivia Database 获取题目...")
    response = requests.get(API_URL, params=PARAMS)
    response.raise_for_status()
    data = response.json()

    if data["response_code"] != 0:
        print(f"API 返回错误码: {data['response_code']}")
        return []

    questions = data["results"]
    print(f"成功获取 {len(questions)} 道题目")

    processed_questions = []
    for idx, q in enumerate(questions, 1):
        question_text = html.unescape(q["question"])
        correct_answer = html.unescape(q["correct_answer"])
        incorrect_answers = [html.unescape(ans) for ans in q["incorrect_answers"]]

        all_answers = incorrect_answers + [correct_answer]
        random.shuffle(all_answers)

        options_json = json.dumps(all_answers, ensure_ascii=False)

        difficulty_map = {
            "easy": "easy",
            "medium": "medium",
            "hard": "hard"
        }

        difficulty = difficulty_map.get(q["difficulty"], "medium")

        if q["type"] == "boolean":
            question_type = "judge"
            options_json = json.dumps(["对", "错"], ensure_ascii=False)
            correct_answer = "对" if correct_answer == "True" else "错"
        else:
            question_type_map = {
                "multiple": "single"
            }
            question_type = question_type_map.get(q["type"], "single")

        processed_questions.append({
            "id": idx,
            "type": question_type,
            "difficulty": difficulty,
            "question": question_text,
            "options": options_json,
            "answer": correct_answer,
            "explanation": f"分类: {html.unescape(q['category'])}",
            "score": 10 if difficulty == "easy" else (20 if difficulty == "medium" else 30)
        })

    return processed_questions

def escape_sql(text):
    return str(text).replace("'", "''")

def save_json(questions, filename="questions.json"):
    filepath = f"../scripts/{filename}"
    with open(filepath, "w", encoding="utf-8") as f:
        json.dump(questions, f, ensure_ascii=False, indent=2)
    print(f"JSON 数据已保存到: {filepath}")

def generate_sql(questions, filename="questions.sql"):
    filepath = f"../scripts/{filename}"
    with open(filepath, "w", encoding="utf-8", newline="") as f:
        f.write("-- 从 Open Trivia Database 获取的计算机科学题目\n")
        f.write(f"-- 生成时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        f.write("-- 分类ID: 18 (计算机科学)\n\n")

        f.write("INSERT INTO questions (type, difficulty, content, options, answer, explanation, score, bank_id, create_time, update_time) VALUES\n")

        values = []
        for q in questions:
            values.append(
                f"  ('{escape_sql(q['type'])}', '{escape_sql(q['difficulty'])}', '{escape_sql(q['question'])}', "
                f"'{escape_sql(q['options'])}', '{escape_sql(q['answer'])}', "
                f"'{escape_sql(q['explanation'])}', {q['score']}, 1, NOW(), NOW())"
            )

        f.write(",\n".join(values) + ";\n")

    print(f"SQL 数据已保存到: {filepath}")

def main():
    try:
        questions = fetch_questions()

        if questions:
            save_json(questions)
            generate_sql(questions)

            print("\n✅ 数据获取成功！")
            print(f"📊 共获取 {len(questions)} 道题目")
            print("\n文件位置:")
            print("  - JSON: scripts/questions.json")
            print("  - SQL:  scripts/questions.sql")
        else:
            print("❌ 获取题目失败")

    except requests.exceptions.RequestException as e:
        print(f"❌ 网络请求失败: {e}")
    except Exception as e:
        print(f"❌ 发生错误: {e}")

if __name__ == "__main__":
    main()
