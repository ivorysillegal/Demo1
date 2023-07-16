package Options;

public class Instructor {
    public static void help() {
        System.out.println("""
                RPOP [KEY]
                LEN [KEY]
                RANGE [KEY][START][END]
                HELP [COMMAND]
                SET [KEY][VALUE]
                DEL [KEY][KEY1][KEY2]
                RPUSH [KEY][VALUE]
                LPOP [KEY]
                LDEL [KEY]
                GET [KEY]
                LPUSH [KEY][VALUE]""");
    }

    public static void help(String method) {
        switch (method) {
            case "set" -> System.out.println("SET [KEY][VALUE]");
            case "rpop" -> System.out.println("RPOP [KEY]");
            case "len" -> System.out.println("LEN [KEY]");
            case "range" -> System.out.println("RANGE [KEY][START][END]");
            case "help" -> System.out.println("HELP [COMMAND]");
            case "del" -> System.out.println("DEL [KEY][KEY1][KEY2]");
            case "rpush" -> System.out.println("RPUSH [KEY][VALUE]");
            case "lpop" -> System.out.println("LPOP [KEY]");
            case "ldel" -> System.out.println("LDEL [KEY]");
            case "get" -> System.out.println("GET [KEY]");
            case "lpush" -> System.out.println("LPUSH [KEY][VALUE]");
        }
    }
}
