import json
import pymysql

def main():
    print("正在读取中文题库数据...")
    with open("questions_zh.json", "r", encoding="utf-8") as f:
        questions = json.load(f)
    
    print(f"已读取 {len(questions)} 道题目")

    print("\n正在连接数据库...")
    try:
        conn = pymysql.connect(
            host='localhost',
            port=3306,
            user='study',
            password='password',
            database='study',
            charset='utf8mb4'
        )
        
        cursor = conn.cursor()
        print("数据库连接成功！")

        print("\n正在插入题目数据...")
        for idx, q in enumerate(questions, 1):
            sql = """
            INSERT INTO questions (type, difficulty, content, options, answer, explanation, score, bank_id, create_time, update_time)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, NOW(), NOW())
            """
            cursor.execute(sql, (
                q['type'],
                q['difficulty'],
                q['question'],
                q['options'],
                q['answer'],
                q['explanation'],
                q['score'],
                1
            ))
            
            if idx % 10 == 0:
                print(f"已插入 {idx} 道题目...")

        conn.commit()
        print(f"\n✅ 成功插入 {len(questions)} 道中文题目！")

    except Exception as e:
        print(f"\n❌ 数据库操作失败: {e}")
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()
            print("数据库连接已关闭")

if __name__ == "__main__":
    main()