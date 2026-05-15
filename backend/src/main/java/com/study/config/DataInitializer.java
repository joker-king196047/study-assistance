package com.study.config;

<<<<<<< HEAD
import com.study.entity.Question;
import com.study.entity.QuestionBank;
import com.study.entity.User;
import com.study.mapper.QuestionBankMapper;
import com.study.mapper.QuestionMapper;
=======
import com.study.entity.User;
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
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
<<<<<<< HEAD
    private final QuestionBankMapper questionBankMapper;
    private final QuestionMapper questionMapper;
=======
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4

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
<<<<<<< HEAD

        if (questionBankMapper.count() == 0) {
            seedData();
            System.out.println("=== 题库种子数据已初始化 ===");
        }
    }

    private void seedData() {
        Integer bank1 = createBank("数据结构与算法", "涵盖线性表、树、图、排序、查找等核心知识点", "计算机", "medium");
        Integer bank2 = createBank("计算机网络", "OSI模型、TCP/IP协议、HTTP、DNS等网络核心知识", "计算机", "medium");
        Integer bank3 = createBank("操作系统", "进程管理、内存管理、文件系统、I/O系统", "计算机", "medium");
        Integer bank4 = createBank("数据库系统", "SQL语言、关系代数、事务管理、索引优化", "计算机", "medium");

        seedDataStructure(bank1);
        seedComputerNetwork(bank2);
        seedOperatingSystem(bank3);
        seedDatabase(bank4);
    }

    private Integer createBank(String name, String desc, String category, String difficulty) {
        QuestionBank bank = new QuestionBank();
        bank.setName(name);
        bank.setDescription(desc);
        bank.setCategory(category);
        bank.setDifficulty(difficulty);
        questionBankMapper.save(bank);
        return bank.getId();
    }

    private void addQ(Integer bankId, String type, String content, String options, String answer, String explanation, String difficulty) {
        Question q = new Question();
        q.setBankId(bankId);
        q.setType(type);
        q.setContent(content);
        q.setOptions(options);
        q.setAnswer(answer);
        q.setExplanation(explanation);
        q.setScore(5);
        q.setDifficulty(difficulty);
        questionMapper.save(q);
    }

    private void seedDataStructure(Integer bankId) {
        addQ(bankId, "single", "在长度为n的顺序表中，删除第i个元素需要移动多少个元素？",
            "[\"A.n-i\",\"B.n-i+1\",\"C.n-i-1\",\"D.i\"]", "A",
            "删除第i个元素后，需要将第i+1到第n个元素依次前移，共移动n-i个元素。", "easy");
        addQ(bankId, "single", "在一个单链表中，若要删除指针p所指结点的后继结点，以下操作正确的是？",
            "[\"A.p->next = p->next->next\",\"B.p = p->next; p->next = p->next->next\",\"C.p->next = p->next\",\"D.p = p->next->next\"]", "A",
            "删除后继结点只需将p的next指针指向后继的后继即可。", "easy");
        addQ(bankId, "single", "栈和队列的共同特点是？",
            "[\"A.都是先进先出\",\"B.都是先进后出\",\"C.只允许在端点处插入和删除元素\",\"D.没有共同点\"]", "C",
            "栈和队列都是操作受限的线性表，只允许在端点处进行插入和删除操作。", "easy");
        addQ(bankId, "single", "一棵完全二叉树有1000个结点，其叶子结点的个数为？",
            "[\"A.500\",\"B.499\",\"C.501\",\"D.250\"]", "A",
            "完全二叉树中，n0 = n2 + 1，且n = n0 + n1 + n2。当n=1000时，n1=1，所以n0 = (1000-1)/2 + 1 = 500。", "medium");
        addQ(bankId, "single", "对n个元素进行快速排序，最坏情况下的时间复杂度为？",
            "[\"A.O(n)\",\"B.O(nlogn)\",\"C.O(n²)\",\"D.O(logn)\"]", "C",
            "快速排序最坏情况是每次划分都极度不平衡，退化为O(n²)。", "easy");
    }

    private void seedComputerNetwork(Integer bankId) {
        addQ(bankId, "single", "OSI参考模型将网络分为几层？",
            "[\"A.4层\",\"B.5层\",\"C.6层\",\"D.7层\"]", "D",
            "OSI参考模型分为7层：物理层、数据链路层、网络层、传输层、会话层、表示层、应用层。", "easy");
        addQ(bankId, "single", "TCP协议是一种什么类型的协议？",
            "[\"A.无连接不可靠\",\"B.无连接可靠\",\"C.面向连接可靠\",\"D.面向连接不可靠\"]", "C",
            "TCP是面向连接的可靠传输协议，通过三次握手建立连接。", "easy");
        addQ(bankId, "single", "HTTP协议默认使用的端口号是？",
            "[\"A.21\",\"B.22\",\"C.80\",\"D.443\"]", "C",
            "HTTP默认端口80，HTTPS默认端口443。", "easy");
        addQ(bankId, "single", "TCP三次握手的正确顺序是？",
            "[\"A.SYN→ACK→SYN+ACK\",\"B.SYN→SYN+ACK→ACK\",\"C.ACK→SYN→SYN+ACK\",\"D.SYN+ACK→SYN→ACK\"]", "B",
            "TCP三次握手：客户端发SYN→服务器回SYN+ACK→客户端发ACK，连接建立。", "easy");
    }

    private void seedOperatingSystem(Integer bankId) {
        addQ(bankId, "single", "进程和线程的主要区别是？",
            "[\"A.进程是资源分配的基本单位，线程是CPU调度的基本单位\",\"B.线程是资源分配的基本单位，进程是CPU调度的基本单位\",\"C.进程和线程没有区别\",\"D.线程不能共享进程的资源\"]", "A",
            "进程是资源分配的基本单位，拥有独立的地址空间；线程是CPU调度的基本单位。", "easy");
        addQ(bankId, "single", "产生死锁的四个必要条件中，不包括以下哪个？",
            "[\"A.互斥条件\",\"B.请求和保持条件\",\"C.可抢占条件\",\"D.循环等待条件\"]", "C",
            "死锁四个必要条件：互斥、请求和保持、不可抢占、循环等待。", "easy");
        addQ(bankId, "single", "虚拟存储器的最大容量取决于？",
            "[\"A.内存大小\",\"B.外存大小\",\"C.计算机地址位数\",\"D.内存和外存大小之和\"]", "C",
            "虚拟存储器的最大容量由计算机的地址位数决定。", "medium");
        addQ(bankId, "single", "页面置换算法LRU的含义是？",
            "[\"A.最近最久未使用\",\"B.最近最少使用\",\"C.先进先出\",\"D.最佳置换\"]", "A",
            "LRU(Least Recently Used)即最近最久未使用。", "easy");
    }

    private void seedDatabase(Integer bankId) {
        addQ(bankId, "single", "SQL中，用于删除表中所有数据但保留表结构的命令是？",
            "[\"A.DROP TABLE\",\"B.DELETE FROM\",\"C.TRUNCATE TABLE\",\"D.REMOVE TABLE\"]", "C",
            "TRUNCATE删除所有数据且不可回滚，但保留表结构。", "easy");
        addQ(bankId, "single", "关系数据库中，主键的作用是？",
            "[\"A.加速查询\",\"B.唯一标识元组\",\"C.建立表间联系\",\"D.保证数据完整性\"]", "B",
            "主键用于唯一标识关系中的每个元组。", "easy");
        addQ(bankId, "single", "事务的ACID特性中，I代表的是？",
            "[\"A.原子性\",\"B.一致性\",\"C.隔离性\",\"D.持久性\"]", "C",
            "ACID：原子性(Atomicity)、一致性(Consistency)、隔离性(Isolation)、持久性(Durability)。", "easy");
        addQ(bankId, "single", "以下哪种索引结构适合范围查询？",
            "[\"A.哈希索引\",\"B.B+树索引\",\"C.位图索引\",\"D.全文索引\"]", "B",
            "B+树的叶子节点通过链表相连，非常适合范围查询。", "easy");
    }
}
=======
    }
}
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
