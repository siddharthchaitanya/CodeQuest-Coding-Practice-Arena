package CodeQuest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final List<Map<String, Object>> questions = new ArrayList<>();

    private static final Pattern QUESTION_PATTERN = Pattern.compile(
            "^([EMH])\\|(.*?)\\|(.+?)\\|([0-3])\\|(.+?)\\|(.+)$"
    );

    public QuizController() {
        String bank = """
                E|What is the output of 5 + 2?|5~7~52~Error|1|Addition gives 7.|System.out.println(5 + 2);
                E|Which keyword creates an object?|class~static~new~void|2|The new keyword creates objects.|Student s = new Student();
                E|Which type stores true or false?|int~boolean~String~double|1|boolean stores true or false.|boolean ready = true;
                E|Which symbol ends a Java statement?|:~;~.~,|1|Most Java statements end with a semicolon.|int age = 18;
                E|What is the Java entry-point method?|start()~run()~main()~init()|2|Execution normally begins in main().|public static void main(String[] args)
                E|Which keyword declares a class?|object~class~define~struct|1|The class keyword declares a class.|class Student {}
                E|Which type stores whole numbers?|int~boolean~char~String|0|int stores integer values.|int count = 10;
                E|Which type stores a single character?|String~char~boolean~int|1|char stores one character.|char grade = 'A';
                E|Which operator checks equality for primitives?|=~==~!=~:=|1|== compares primitive values.|System.out.println(4 == 4);
                E|Which operator means logical AND?|&&~||~!~&=|0|&& is logical AND.|true && false
                E|Which keyword returns a value from a method?|break~return~continue~void|1|return sends a value back to the caller.|return total;
                E|Which loop executes while a condition is true?|if~while~switch~class|1|while repeats as long as its condition is true.|while (x < 5) {}
                E|Which statement selects between alternatives?|package~if~import~new|1|if runs code when its condition is true.|if (age >= 18) {}
                E|Which keyword exits a loop immediately?|skip~stop~break~next|2|break terminates the loop.|break;
                E|Which keyword skips to the next loop iteration?|break~continue~return~exit|1|continue skips the rest of that iteration.|continue;
                E|Which method prints text with a newline?|print()~println()~writeLine()~display()|1|println prints and then moves to a new line.|System.out.println("Hi");
                E|Which type stores decimal values with double precision?|int~boolean~double~char|2|double stores floating-point values.|double price = 9.99;
                E|How do you declare a String variable?|text name~String name~string name~char name|1|Java uses the String class.|String name = "Sam";
                E|What is the first array index in Java?|0~1~-1~Depends on type|0|Java arrays start at index zero.|int[] a = {10, 20};
                E|Which keyword declares a constant-like variable?|fixed~const~final~staticonly|2|final prevents reassignment of a variable.|final int MAX = 100;
                E|What is the result of 10 / 3 with integers?|3.33~3~4~Error|1|Integer division discards the fractional part.|System.out.println(10 / 3);
                E|Which keyword inherits a class?|implements~inherits~extends~super|2|extends establishes class inheritance.|class Dog extends Animal {}
                E|Which collection stores unique elements?|ArrayList~HashSet~LinkedList~Vector|1|HashSet does not allow duplicate elements.|Set<Integer> s = new HashSet<>();
                E|How many elements are in an array of length 5?|4~5~6~Depends on values|1|length gives the number of array elements.|int[] a = new int[5];
                E|What does String.length() return?|Last index~Character count~Byte count~Always zero|1|String.length() returns the number of characters in the string.|"Java".length()
                E|Which access modifier allows access from anywhere, subject to module/package rules?|private~protected~public~default|2|public is the broadest ordinary access modifier.|public int score;
                E|Which keyword refers to the current object?|super~this~self~current|1|this refers to the current instance.|this.name = name;
                E|Which keyword refers to a superclass?|parent~base~super~this|2|super accesses superclass members and constructors.|super();
                E|Which keyword is used to implement an interface?|extends~implements~inherits~interface|1|A class uses implements for an interface.|class Dog implements Runnable {}
                E|Which type represents a 64-bit integer?|short~int~long~byte|2|long is a 64-bit signed integer type.|long population = 8000000L;
                E|Which loop is commonly used to iterate over an array?|foreach only~enhanced for~switch loop~case loop|1|The enhanced for loop visits each element.|for (int n : nums) {}
                E|Which class is commonly used for keyboard input?|Scanner~Printer~ReaderOnly~ConsoleWriter|0|Scanner can read input from System.in.|Scanner sc = new Scanner(System.in);
                E|Which package contains ArrayList?|java.io~java.util~java.net~java.sql|1|ArrayList belongs to java.util.|import java.util.ArrayList;
                E|Which keyword handles an exception?|catch~throws~final~assert|0|catch handles matching exceptions.|try {} catch (Exception e) {}
                E|Which block is used with try to handle exceptions?|catch~package~import~class|0|A catch block handles exceptions.|catch (Exception e) {}
                E|Which keyword declares a method that belongs to the class rather than an instance?|final~static~public~this|1|static members belong to the class.|static int count;
                E|What is the default value of an int instance field?|1~0~null~-1|1|An int field defaults to zero.|int number;
                E|What is the default value of a boolean instance field?|true~false~null~0|1|A boolean field defaults to false.|boolean active;
                E|Which keyword prevents a method from being overridden?|static~final~private only~const|1|A final method cannot be overridden.|final void show() {}
                M|What is the output of System.out.println("Java".length())?|3~4~5~Error|1|Java has four characters.|"Java".length()
                M|Which statement about HashMap keys is correct?|Keys must be unique~Values must be unique~Only integer keys allowed~Keys are always sorted|0|A key can appear only once; adding it again replaces its value.|Map<String, Integer> map = new HashMap<>();
                M|What is method overloading?|Same name with different parameter lists~Same method body copied~Deleting a method~Changing only return type|0|Overloading uses different parameter lists.|int add(int a, int b) { return a + b; }
                M|Which exception can occur from an invalid array index?|IOException~NullPointerException~ArrayIndexOutOfBoundsException~ClassNotFoundException|2|Accessing an invalid array index causes this exception.|int[] a = {1}; System.out.println(a[3]);
                M|Which keyword prevents a class from being extended?|static~private~final~const|2|A final class cannot be subclassed.|final class Secure {}
                M|What is the output of 7 % 3?|1~2~3~0|0|The remainder of 7 divided by 3 is 1.|System.out.println(7 % 3);
                M|What does a constructor do?|Destroys an object~Initializes a new object~Imports a package~Runs every minute|1|A constructor initializes an instance when it is created.|Student() {}
                M|Which is a valid constructor name?|create~initStudent~Must match class name~main|2|A constructor has the same name as its class.|class Student { Student() {} }
                M|Can a Java class extend multiple classes?|Yes, any number~Yes, exactly two~No, only one class~No inheritance at all|2|Java classes have single class inheritance.|class Dog extends Animal {}
                M|Can a Java class implement multiple interfaces?|No~Yes~Only if abstract~Only two|1|A class can implement multiple interfaces.|class C implements A, B {}
                M|What does == compare for object references?|Always object contents~Reference identity~Only class names~String length|1|For objects, == tests whether references point to the same object.|a == b
                M|Which method compares String contents?|==~equals()~compareRef()~same()|1|equals() compares String contents.|"cat".equals("cat")
                M|Which String method returns a substring?|slice()~substring()~cut()~extract()|1|substring extracts part of a String.|"coding".substring(1, 4)
                M|What is the result of "Java".charAt(1)?|J~a~v~Error|1|String indices start at zero.|"Java".charAt(1)
                M|Which collection maintains insertion order and allows duplicates?|HashSet~ArrayList~TreeSet~HashMap|1|ArrayList preserves order and permits duplicate elements.|List<Integer> a = new ArrayList<>();
                M|Which collection sorts elements by natural order?|HashSet~TreeSet~ArrayDeque~LinkedList|1|TreeSet orders its elements.|Set<Integer> s = new TreeSet<>();
                M|Which interface represents a first-in-first-out queue?|Queue~Map~Set~Comparable|0|Queue models elements waiting to be processed.|Queue<String> q = new LinkedList<>();
                M|Which method removes and returns the head of a queue?|peek()~poll()~offer()~size()|1|poll retrieves and removes the head, or returns null if empty.|q.poll();
                M|Which method examines a queue head without removing it?|remove()~peek()~clear()~push()|1|peek examines the head without removing it.|q.peek();
                M|Which data structure follows last-in-first-out order?|Queue~Stack~Tree~Graph|1|A stack removes the most recently added item first.|Deque<Integer> stack = new ArrayDeque<>();
                M|Which keyword is used to create a subclass object?|extends~new~this~super|1|new creates an object of the specified class.|Animal a = new Dog();
                M|What is polymorphism in object-oriented programming?|One form only~Objects can have multiple forms through a common type~No inheritance~Variables cannot change|1|Polymorphism allows different implementations through a common type.|Animal a = new Dog();
                M|What is encapsulation?|Combining data and methods while controlling access~Sorting arrays~Multiple inheritance~Compiling source files|0|Encapsulation protects internal state through controlled access.|private int age;
                M|What is abstraction?|Showing essential features while hiding implementation details~Making every field public~Removing all methods~Copying classes|0|Abstraction focuses on essential behavior rather than implementation details.|abstract class Shape {}
                M|Which keyword declares an abstract class?|virtual~abstract~interface-only~sealed|1|abstract marks a class or method as abstract.|abstract class Shape {}
                M|Can an abstract class be instantiated directly?|Yes, always~No~Only with static~Only in main|1|An abstract class cannot be instantiated directly.|abstract class Shape {}
                M|What happens when a subclass overrides a method?|Subclass supplies its implementation~Superclass is deleted~Method becomes a constructor~Program always fails|0|Overriding provides a subclass implementation of an inherited method.|@Override public void run() {}
                M|Which annotation commonly marks an overridden method?|@Inherited~@Override~@Static~@Replace|1|@Override helps the compiler verify overriding.|@Override
                M|Which keyword explicitly throws an exception?|throws~throw~catch~finally|1|throw throws a particular exception object.|throw new IllegalArgumentException();
                M|What does finally usually do in a try-catch structure?|Runs only on success~Runs after try/catch in normal completion paths~Declares a variable~Prevents compilation|1|finally is used for cleanup and normally runs whether or not an exception occurs.|finally { cleanup(); }
                M|Which is a checked exception?|IOException~NullPointerException~ArithmeticException~IllegalArgumentException|0|IOException is checked at compile time.|throws IOException
                M|Which block can close resources automatically?|try-with-resources~switch~synchronized only~static block|0|Try-with-resources closes AutoCloseable resources.|try (Scanner sc = new Scanner(System.in)) {}
                M|What does the static keyword mean for a field?|One copy associated with the class~A new copy per object~Always constant~Cannot be accessed|0|A static field is shared at the class level.|static int count;
                M|Which keyword makes a field not directly accessible outside its class?|public~private~protected~static|1|private restricts direct access to the declaring class.|private int balance;
                M|Which access modifier permits package access without a keyword?|public~private~protected~default access|3|No modifier gives package-private access.|int count;
                M|What is the purpose of a package declaration?|Group related classes and define their namespace~Run the program~Create an object~Catch errors|0|Packages organize classes and avoid naming conflicts.|package com.example;
                M|Which keyword brings another type into scope by name?|include~using~import~require|2|import lets source code refer to types by simple name.|import java.util.List;
                M|Which method converts a String such as "42" to an int?|Integer.parseInt()~String.toInt()~int.value()~parse.Integer()|0|Integer.parseInt converts a numeric String to an int.|Integer.parseInt("42")
                M|What happens when an int is cast to a double?|Fractional precision is added to the integer value~It becomes a String~It always fails~It becomes boolean|0|Widening conversion from int to double is automatic.|double d = (double) 5;
                M|Which statement about Java is correct?|Java is purely interpreted~Java compiles to bytecode executed by the JVM~Java needs no runtime~Java only runs on Linux|1|Java source is compiled to bytecode, which the JVM executes.|javac Main.java
                M|What does JVM stand for?|Java Variable Method~Java Virtual Machine~Joint Virtual Memory~Java Verified Mode|1|The JVM executes Java bytecode.|java Main
                M|Which tool compiles Java source code?|java~javac~javadoc only~jar only|1|javac is the Java compiler.|javac Main.java
                M|What is the purpose of the main method's String[] args?|Store command-line arguments~Store all class fields~Return an integer~Create the JVM|0|args contains command-line arguments.|public static void main(String[] args)
                M|Which keyword starts a conditional multi-branch statement?|switch~loop~choose~caseonly|0|switch selects among matching cases.|switch (n) {}
                M|What is the result of true || false?|true~false~Error~null|0|Logical OR is true when at least one operand is true.|true || false
                M|What is the result of !true?|true~false~1~Error|1|Logical NOT reverses a boolean value.|!true
                M|Which operator increments a variable by one?|--~++~**~//|1|++ increments a numeric variable.|x++;
                M|What is the result of 2 + 3 * 4?|20~14~24~Error|1|Multiplication takes precedence over addition.|2 + 3 * 4
                M|Which array property gives its size?|size()~length~length()~count()|1|Arrays use the length field.|arr.length
                H|What is the output of System.out.println(5 + "2")?|7~52~Error~25|1|When one operand is a String, + concatenates.|System.out.println(5 + "2");
                H|What does Integer.parseInt("12x") do?|Returns 12~Returns zero~Throws NumberFormatException~Returns null|2|The text is not a valid integer representation.|Integer.parseInt("12x")
                H|Which statement about String objects is true?|Strings are mutable~Strings are immutable~Strings are primitive types~Strings cannot be compared|1|String operations create new strings rather than modifying the original.|String s = "Java";
                H|What is the result of "hello".toUpperCase()?|hello~HELLO~Hello~Error|1|toUpperCase returns an uppercase String.|"hello".toUpperCase()
                H|Which map implementation generally preserves insertion order?|HashMap~TreeMap~LinkedHashMap~Hashtable sorts keys|2|LinkedHashMap maintains insertion order.|Map<Integer, String> m = new LinkedHashMap<>();
                H|Which map implementation orders keys by their natural ordering by default?|HashMap~TreeMap~IdentityHashMap~WeakHashMap|1|TreeMap sorts keys using natural ordering or a comparator.|Map<Integer, String> m = new TreeMap<>();
                H|What does List.of(1, 2, 3) return?|A mutable ArrayList~An unmodifiable list~A HashSet~An array|1|List.of creates an unmodifiable list.|List<Integer> n = List.of(1, 2, 3);
                H|What happens when adding to a List created by List.of()?|Always succeeds~UnsupportedOperationException~NullPointerException always~Compilation fails|1|The list is unmodifiable.|List.of(1, 2).add(3);
                H|Which functional interface takes one argument and returns a result?|Runnable~Consumer~Function~Supplier|2|Function<T,R> accepts a T and returns an R.|Function<Integer, Integer> f = x -> x * 2;
                H|Which functional interface accepts an argument and returns no result?|Supplier~Consumer~Function~Callable|1|Consumer accepts a value and returns no result.|Consumer<String> c = s -> System.out.println(s);
                H|Which functional interface supplies a value without an argument?|Supplier~Predicate~Consumer~BiConsumer|0|Supplier.get() returns a value.|Supplier<Integer> s = () -> 10;
                H|Which functional interface tests a condition and returns boolean?|Predicate~Supplier~Consumer~Runnable|0|Predicate.test returns a boolean.|Predicate<Integer> p = x -> x > 0;
                H|What does a lambda expression provide?|A concise implementation of a functional interface method~A new package~A database connection~A class constructor only|0|Lambdas express behavior for functional interfaces.|x -> x * 2
                H|Which stream operation filters elements by a condition?|map()~filter()~collect()~sorted()|1|filter retains elements matching a predicate.|numbers.stream().filter(n -> n > 5)
                H|Which stream operation transforms each element?|filter()~map()~count()~findFirst()|1|map transforms elements into another form.|numbers.stream().map(n -> n * 2)
                H|Which stream operation is terminal and counts elements?|filter()~map()~count()~peek()|2|count is a terminal operation returning a long.|numbers.stream().count()
                H|What does Optional help represent?|A value that may be absent~Only a list~A thread~A primitive integer only|0|Optional can represent a present or absent value.|Optional<String> name = Optional.empty();
                H|Which keyword is used to synchronize a Java method or block?|volatile~synchronized~transient~native|1|synchronized coordinates access using a monitor lock.|synchronized void update() {}
                H|What does volatile guarantee for a shared variable?|Atomicity for all operations~Visibility of writes between threads~Mutual exclusion~No memory usage|1|volatile provides visibility and ordering guarantees, not general atomicity.|volatile boolean running = true;
                H|Which method starts a new Thread's execution?|run() directly~start()~executeNow()~begin()|1|start schedules a new thread; the JVM invokes run().|new Thread(task).start();
                H|Which method waits for another thread to finish?|waitFor()~join()~sleepForever()~finish()|1|join waits for a thread to terminate.|thread.join();
                H|What is a deadlock?|A thread waiting forever due to cyclic resource dependencies~A syntax error~An empty array~A successful build|0|Deadlock can occur when threads wait on each other's locked resources.|synchronized(lock) {}
                H|Which collection is designed for thread-safe concurrent key-value access?|HashMap~ConcurrentHashMap~TreeSet~ArrayList|1|ConcurrentHashMap supports concurrent map operations.|Map<String, Integer> m = new ConcurrentHashMap<>();
                H|What is the output of 1 + 2 + "3"?|33~123~6~Error|0|Addition happens first, then String concatenation.|System.out.println(1 + 2 + "3");
                H|What is the output of "1" + 2 + 3?|6~123~15~Error|1|Evaluation proceeds left to right, concatenating each later value.|System.out.println("1" + 2 + 3);
                H|Which method checks whether a collection contains an element?|has()~contains()~exists()~includes()|1|contains checks for a matching element.|list.contains("Java")
                H|Which method removes all elements from a collection?|deleteAll()~clear()~removeAllNow()~empty()|1|clear removes every element from the collection.|list.clear()
                H|What is the result of Math.max(4, 9)?|4~9~13~Error|1|Math.max returns the larger argument.|Math.max(4, 9)
                H|Which class provides arbitrary-precision integer arithmetic?|Long~BigInteger~IntegerArray~Decimal|1|BigInteger supports integers beyond primitive ranges.|BigInteger.valueOf(100L)
                H|Which principle says a subclass should be usable wherever its superclass is expected?|Single responsibility~Liskov substitution~Open addressing~Encapsulation only|1|The Liskov substitution principle concerns safe substitutability of subtypes.|Animal a = new Dog();
                H|What is a Java record mainly intended for?|Concise data-carrier classes~Starting threads~Managing packages~Replacing the JVM|0|Records provide a concise way to declare data-oriented classes.|record Point(int x, int y) {}
                """;

        int id = 1;

        for (String rawLine : bank.strip().split("\\R")) {
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }

            Matcher matcher = QUESTION_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalStateException("Invalid question data: " + line);
            }

            String difficulty = matcher.group(1);
            String question = matcher.group(2);
            List<String> options = List.of(matcher.group(3).split("~", -1));
            int answer = Integer.parseInt(matcher.group(4));
            String explanation = matcher.group(5);
            String code = matcher.group(6);

            if (options.size() != 4 || answer < 0 || answer >= options.size()) {
                throw new IllegalStateException("Invalid options or answer index: " + line);
            }

           int questionId = id++;

int level = questionId <= 11
        ? 1
        : ((questionId - 12) / 12) + 2;

questions.add(Map.of(
        "id", questionId,
        "level", level,
        "question", question,
        "options", options,
        "answer", answer,
        "explanation", explanation,
        "difficulty", difficulty,
        "code", code
));
        }
    }

   @GetMapping("/questions")
public List<Map<String, Object>> getQuestions(
        @RequestParam(required = false) Integer level) {

    List<Map<String, Object>> quiz = new ArrayList<>(questions);

    if (level != null) {
        quiz.removeIf(q -> !q.get("level").equals(level));
        return quiz;
    }

    Collections.shuffle(quiz);
    return quiz;
}
}
