package com.study.config;

import com.study.entity.Question;
import com.study.entity.User;
import com.study.mapper.QuestionMapper;
import com.study.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final QuestionMapper questionMapper;

    @Override
    public void run(String... args) {
        if (!userMapper.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("password"));
            userMapper.save(admin);
            System.out.println("=== 预设账号已创建 ===");
            System.out.println("用户名: admin");
            System.out.println("密码: password");
            System.out.println("====================");
        }

        if (questionMapper.count() == 0) {
            seedQuestions();
            System.out.println("=== 题库种子数据已初始化 ===");
        }
    }

    private void seedQuestions() {
        add("P1001", "A+B Problem", "工学", "cs-data-structure", "输入两个整数 a 和 b，输出它们的和。", "入门", "洛谷", "1 2", "3");
        add("P1035", "级数求和", "工学", "cs-data-structure", "已知 S_n = 1 + 1/2 + 1/3 + ... + 1/n。对于给定的整数 K，求最小的 n 使得 S_n > K。", "普及-", "洛谷", "1", "2");
        add("P1046", "陶陶摘苹果", "工学", "cs-data-structure", "陶陶家的院子里有一棵苹果树，每到秋天树上就会结出10个苹果。苹果成熟的时候，陶陶就会跑去摘苹果。陶陶有个30厘米高的板凳，当她不能直接用手摘到苹果时，就会踩到板凳上试试。现在已知10个苹果到地面的高度，以及陶陶把手伸直时能达到的最大高度，请帮陶陶算一下她能够摘到的苹果数目。", "入门", "洛谷", "100 200 150 140 129 134 167 198 200 111\n110", "5");
        add("P1055", "ISBN号码", "工学", "cs-data-structure", "每一本正式出版的图书都有一个ISBN号码与之对应。ISBN码包括9位数字、1位识别码和3位分隔符，其规定格式如x-xxx-xxxxx-x。最后一位是识别码，通过前9位数字按特定公式计算得出。判断输入的ISBN号码中识别码是否正确。", "普及-", "洛谷", "0-670-82162-4", "Right");
        add("P1085", "不高兴的津津", "工学", "cs-data-structure", "津津上初中了。妈妈认为津津应该更加用功学习，所以津津除了上学之外，还要参加妈妈为她报名的各科复习班。另外每周妈妈还会送她去学习朗诵、舞蹈和钢琴。但是津津如果一天上课超过八个小时就会不高兴，而且上得越久就会越不高兴。假设津津不会因为其它事不高兴，但是她的不高兴会持续到第二天。请你帮忙检查津津下周的日程安排，看看下周她会不会不高兴；如果会的话，哪天最不高兴。", "入门", "洛谷", "5 3\n6 2\n7 2\n5 3\n5 4\n0 4\n0 6", "3");
        add("P1425", "小鱼的游泳时间", "工学", "cs-data-structure", "小鱼从 a 时 b 分开始游泳，到 c 时 d 分结束。请计算小鱼一共游了多长时间。", "入门", "洛谷", "12 50 19 10", "6 20");
        add("P1421", "小玉买文具", "工学", "cs-data-structure", "班主任给小玉一个任务，到文具店里买尽量多的签字笔。已知一只签字笔的价格是1元9角，而班主任给小玉的钱是a元b角。请问小玉最多能买多少只签字笔。", "入门", "洛谷", "10 3", "5");
        add("P1909", "买铅笔", "工学", "cs-data-structure", "P老师需要去商店买n支铅笔作为小朋友们参加NOIP的礼物。商店有3种包装的铅笔，不同包装内的铅笔数量有可能不同，价格也有可能不同。P老师决定只买同一种包装的铅笔，同时不允许把包装拆开。请计算P老师最少需要花费多少钱。", "普及-", "洛谷", "57\n2 2\n50 30\n30 27", "54");
        add("P1089", "津津的储蓄计划", "工学", "cs-data-structure", "津津的零花钱一直都是自己管理。每个月的月初妈妈给津津300元钱，津津会预算这个月的花销，并且总能做到实际花销和预算相同。为了让津津学习如何储蓄，妈妈提出，津津可以随时把整百的钱存在她那里，到了年末她会加上20%还给津津。请判断津津的储蓄计划是否会出现资金紧张的情况。", "普及-", "洛谷", "290\n230\n280\n200\n300\n170\n340\n50\n90\n80\n200\n60", "-7");
        add("P1036", "选数", "工学", "cs-data-structure", "已知 n 个整数 x1,x2,...,xn，以及一个整数 k（k < n）。从 n 个整数中任选 k 个整数相加，可分别得到一系列的和。请计算出和为素数的共有多少种。", "普及-", "洛谷", "4 3\n3 7 12 19", "1");
        add("P1149", "火柴棒等式", "工学", "cs-data-structure", "给你n根火柴棍，你可以拼出多少个形如A+B=C的等式？等式中的A、B、C是用火柴棍拼出的整数。加号与等号各自需要两根火柴棍。如果A≠B，则A+B=C与B+A=C视为不同的等式。", "普及-", "洛谷", "18", "9");
        add("P1217", "回文质数", "工学", "cs-data-structure", "找出范围 [a, b] 内的所有回文质数。回文质数即既是回文数又是质数的数。", "普及-", "洛谷", "5 500", "5\n7\n11\n101\n131\n151\n181\n191\n313\n353\n373\n383");
        add("P1307", "数字反转", "工学", "cs-data-structure", "给定一个整数 N，请将该数各个位上数字反转得到一个新数。新数也应满足整数的常见形式，即除非给定的原数为零，否则反转后得到的新数的最高位数字不应为零。", "入门", "洛谷", "123", "321");
        add("P1423", "小玉在游泳", "工学", "cs-data-structure", "小玉开心的在游泳，可是她很快发现自己的力气不够了。已知小玉第一步能游2米，可是随着越来越累，力气越来越小，她接下来的每一步都只能游出上一步距离的98%。现在小玉想知道，如果要游到距离s米的地方，她需要游多少步。", "入门", "洛谷", "4.3", "3");
        add("P1553", "数字反转（升级版）", "工学", "cs-data-structure", "给定一个数，请将该数各个位上数字反转得到一个新数。这次与NOIP2011普及组第一题不同的是：这个数可以是小数、分数、百分数、整数。整数反转是将所有数位对调；小数反转是把整数部分的数反转，再将小数部分的数反转，不交换整数部分与小数部分；分数反转是把分母的数反转，再把分子的数反转，不交换分子与分母；百分数的分子一定是整数，百分数只改变数字部分。", "普及-", "洛谷", "5087462", "2647805");
        add("P1002", "过河卒", "工学", "cs-data-structure", "棋盘上A点有一个过河卒，需要走到目标B点。卒行走的规则：可以向下、或者向右。同时在棋盘上C点有一个对方的马，该马所在的点和所有跳跃一步可达的点称为对方马的控制点。卒不能走到对方马的控制点上。请计算出卒从A点能够到达B点的路径条数。", "普及-", "洛谷", "6 6 3 3", "6");
        add("P1047", "校门外的树", "工学", "cs-data-structure", "某校大门外长度为L的马路上有一排树，每两棵相邻的树之间的间隔都是1米。由于马路上有一些区域要用来建地铁，这些区域用它们在数轴上的起始点和终止点表示。已知任一区域的起始点和终止点的坐标都是整数，区域之间可能有重合的部分。现在要把这些区域中的树（包括区域端点处的两棵树）移走。请计算将这些树都移走后，马路上还有多少棵树。", "入门", "洛谷", "500 3\n150 300\n100 200\n470 471", "298");
        add("P1059", "明明的随机数", "工学", "cs-data-structure", "明明想在学校中请一些同学一起做一项问卷调查。为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤100）。对于其中重复的数字，只保留一个，把其余相同的数去掉。然后再把这些数从小到大排序。请你协助明明完成去重与排序的工作。", "入门", "洛谷", "10\n20 40 32 67 40 20 89 300 400 15", "8\n15 20 32 40 67 89 300 400");
        add("P1200", "你的飞碟在这儿", "工学", "cs-data-structure", "众所周知，在每一个彗星后都有一只UFO。这些UFO时常来收集地球上的忠诚支持者。不幸的是，他们的飞碟每次出行都只能带上一组支持者。他们用一种聪明的方法来确定某小组是否会被彗星带走：小组名和彗星名都用以下方式转换成数字：最终的数字就是名字中所有字母的积除以47的余数。如果小组的数字等于彗星的数字，你就得准备好被带走。", "入门", "洛谷", "COMETQ\nHVNGAT", "GO");
        add("P1308", "统计单词数", "工学", "cs-data-structure", "一般的文本编辑器都有查找单词的功能，该功能可以快速定位特定单词在文章中的位置，有的还能统计出特定单词在文章中出现的次数。请编程实现这一功能：给定一个单词和一个文章段落，找出给定单词在文章中出现的次数和第一次出现的位置。", "普及-", "洛谷", "To\nto be or not to be is a question", "2 0");
        add("P1554", "梦中的统计", "工学", "cs-data-structure", "Bessie的大脑反应灵敏，仿佛真实地看到了她数过的一个又一个数字。她开始注意每一个数码（0..9）：每一个数码在计数的过程中出现过多少次？给出两个整数M和N，求在序列[M, M+1, M+2, ..., N-1, N]中每一个数码出现了多少次。", "入门", "洛谷", "129 137", "1 10 2 9 1 1 1 1 0 1");
        add("P1598", "垂直柱状图", "工学", "cs-data-structure", "写一个程序从输入文件中去读取四行大写字母组成的文本，输出一个垂直柱状图，显示出每个大写字母出现的次数。", "入门", "洛谷", "THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG.\nTHIS IS AN EXAMPLE TO TEST FOR YOUR\nHISTOGRAM PROGRAM.\nHELLO!", "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        add("P1914", "小书童——密码", "工学", "cs-data-structure", "某蒟蒻迷上了密码。密码是由原文字符串（由不超过50个小写字母组成）中每个字母向后移动n位形成的。z的下一个字母是a，如此循环。请找出密码。", "入门", "洛谷", "1\nqwe", "rxf");
        add("P1125", "笨小猴", "工学", "cs-data-structure", "笨小猴的词汇量很小，所以每次做英语选择题的时候都很头疼。但是他找到了一种方法：如果单词中出现次数最多的字母的出现次数减去出现次数最少的字母的出现次数是一个质数，那么笨小猴就认为这是个Lucky Word。请判断输入的单词是不是Lucky Word。", "普及-", "洛谷", "error", "Lucky Word\n2");
        add("P1601", "A+B Problem（高精）", "工学", "cs-data-structure", "高精度加法，相当于a+b problem，不用考虑负数。", "普及-", "洛谷", "1\n2", "3");
        add("P2142", "高精度减法", "工学", "cs-data-structure", "高精度减法。", "普及-", "洛谷", "2\n1", "1");
        add("P1303", "A*B Problem", "工学", "cs-data-structure", "求两数的积。", "普及-", "洛谷", "1\n2", "2");
        add("P1226", "快速幂", "工学", "cs-data-structure", "给你三个整数 a, b, p，求 a^b mod p。", "普及-", "洛谷", "2 10 9", "7");
        add("P1010", "幂次方", "工学", "cs-data-structure", "任何一个正整数都可以用2的幂次方表示。例如：137=2^7+2^3+2^0。同时约定方次用括号来表示，即a^b可表示为a(b)。由此可知，137可表示为：2(7)+2(3)+2(0)。进一步：7=2^2+2+2^0，3=2+2^0。所以最后137可表示为：2(2(2)+2+2(0))+2(2+2(0))+2(0)。请将输入的正整数用此形式表示。", "普及-", "洛谷", "1315", "2(2(2+2(0))+2)+2(2(2+2(0)))+2(2(2)+2(0))+2+2(0)");
        add("P1028", "数的计算", "工学", "cs-data-structure", "我们要求找出具有下列性质数的个数（包含输入的正整数 n）。先输入一个正整数 n，然后对此正整数按照如下方法进行处理：不作任何处理；在它的左边拼接一个正整数，但该正整数不能超过原数的一半；加上数后，继续按此规则进行处理，直到不能再加正整数为止。", "普及-", "洛谷", "6", "6");
        add("P1464", "Function", "工学", "cs-data-structure", "对于一个递归函数 w(a, b, c)：如果 a≤0 或 b≤0 或 c≤0 就返回值1；如果 a>20 或 b>20 或 c>20 就返回 w(20, 20, 20)；如果 a<b 并且 b<c 就返回 w(a, b, c-1) + w(a, b-1, c-1) - w(a, b-1, c)；其它情况返回 w(a-1, b, c) + w(a-1, b-1, c) + w(a-1, b, c-1) - w(a-1, b-1, c-1)。请实现这个带记忆化的递归函数。", "普及-", "洛谷", "1 1 1\n2 2 2\n-1 -1 -1", "w(1, 1, 1) = 2\nw(2, 2, 2) = 4");
        add("P5534", "等差数列", "工学", "cs-data-structure", "等差数列：X1 = A, X2 = A + B, X3 = A + 2B, ...。求 X1 + X2 + ... + Xn 的值。", "入门", "洛谷", "1 2 3", "12");
        add("P5743", "猴子吃桃", "工学", "cs-data-structure", "一只小猴买了若干个桃子。第一天他刚好吃了这些桃子的一半，又多吃了一个；第二天他也刚好吃了剩下桃子的一半，又多吃了一个；以后每天都吃前一天剩下桃子的一半多一个。到第n天早上想再吃的时候，只剩下一个桃子了。请问小猴买了几个桃子？", "入门", "洛谷", "4", "22");
        add("P5744", "培训", "工学", "cs-data-structure", "某培训机构的学员有如下信息：姓名（字符串）、年龄（周岁，整数）、去年NOIP成绩（整数）。经过一年的培训，所有同学的成绩都有所提高，提升了20%（当然NOIP满分是600分，不能超过这个得分）。请输出培训后学员的信息。", "入门", "洛谷", "3\nkkksc03 24 0\nchen_zhe 14 400\nnzhtl1477 18 590", "kkksc03 25 0\nchen_zhe 15 480\nnzhtl1477 19 600");
        add("P5740", "最厉害的学生", "工学", "cs-data-structure", "现有 N 名同学参加了期末考试，并且获得了每名同学的信息：姓名（不超过8个字符的字符串）、语文、数学、英语成绩（均为不超过150的自然数）。总分最高的学生就是最厉害的，请输出最厉害的学生各项信息。", "入门", "洛谷", "3\nsenpai 114 51 4\nlxl 114 10 23\nfafa 51 42 60", "senpai 114 51 4 169");
        add("P5736", "质数筛", "工学", "cs-data-structure", "输入 n 个不大于 100000 的正整数。要求全部储存在数组中，去除掉不是质数的数字，依次输出剩余的质数。", "入门", "洛谷", "5\n3 4 5 6 7", "3 5 7");
        add("P5739", "计算阶乘", "工学", "cs-data-structure", "求 n!，也就是 1×2×3×...×n。", "入门", "洛谷", "3", "6");
        add("P5741", "旗鼓相当的对手 - 加强版", "工学", "cs-data-structure", "现有 N 名同学参加了期末考试，并且获得了每名同学的信息：姓名、语文、数学、英语成绩。如果某对学生 <i,j> 的每一科成绩的分差都不大于5，且总分分差不大于10，那么这对学生就是旗鼓相当的对手。请找出所有旗鼓相当的对手。", "入门", "洛谷", "3\nfafa 90 90 90\nlxl 95 85 90\nsenpai 100 80 91", "fafa lxl\nlxl senpai");
    }

    private void add(String pid, String title, String category, String subCategory,
                     String description, String difficulty, String source,
                     String sampleInput, String sampleOutput) {
        Question q = new Question();
        q.setPid(pid);
        q.setTitle(title);
        q.setCategory(category);
        q.setSubCategory(subCategory);
        q.setDescription(description);
        q.setDifficulty(difficulty);
        q.setSource(source);
        q.setSampleInput(sampleInput);
        q.setSampleOutput(sampleOutput);
        q.setAcceptedCount(0);
        q.setSubmissionCount(0);
        questionMapper.save(q);
    }
}
