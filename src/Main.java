import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------");
        System.out.println("書籍管理システム");
        System.out.println("--------------");

        int exit = 1;
        while (exit == 1) {
            int i = 1;
            while (i == 1) {
                System.out.println("");
                System.out.println("操作を選んでください");
                System.out.println("1:一覧" + " " + "2:検索" + " " + "3:追加" + " " + "4:削除" + " " + "q:終了");
                System.out.print(">>");
                int j=1;
                while (j == 1) {
                    String n = sc.nextLine();
                    //終了
                    try{
                        if(n.equals("q")||n.equals("ｑ")){
                            throw new OriginalException("終了します");
                        }
                    }catch(OriginalException e){
                        System.out.println(e.getMessage());
                        System.exit(0);
                    }
                    //1~4とq以外のものを受け取った時
                    try {
                        if (!(n.equals("1")||n.equals("2")||n.equals("3")||n.equals("4")||n.equals("q")
                                ||n.equals("１")||n.equals("２")||n.equals("３")||n.equals("４")||n.equals("ｑ"))) {
                            throw new OriginalException("1~4またはqを入力してください");
                        }
                    } catch (OriginalException e) {
                        System.out.println(e.getMessage());
                        j = 2;
                        break;
                    }
                    //一覧検索
                    if (n.equals("1")||n.equals("１")) {
                        System.out.println("データベースに登録されているすべての本を出力しますか？");
                        System.out.println("すべての本を出力する場合は1,それ以外は9を入力してください");
                        String choose = sc.nextLine();
                        if(choose.equals("9")||choose.equals("９")){
                            break;
                        }else if(choose.equals("1")||choose.equals("１")) {
                            ArrayList<BookBean> results = BookDAO.selectAll();
                            if (!results.isEmpty()) {
                                for (BookBean rs : results) {
                                    System.out.println("ID        ：" + rs.getId());
                                    System.out.println("本のタイトル：" + rs.getTitle());
                                    System.out.println("著者       ：" + rs.getAuthor());
                                    System.out.println("ーーー");
                                }
                            }
                        }else{
                            System.out.println("1と9以外の文字が選択されたため、操作画面に戻ります");
                            break;
                        }
                    }
                    //ID検索,タイトルと著者名のあいまい検索
                    else if (n.equals("2")||n.equals("２")) {
                        System.out.println("ID検索の場合は1を、タイトル検索の場合は5を、著者名検索の場合は9を、" +
                                "検索ではない場合は1と5と9以外の数字を入力してください");
                        String kensaku = sc.nextLine();
                        if (kensaku.equals("5")||kensaku.equals("５")) {
                            System.out.print("タイトルのワードを入力➡");
                            ArrayList<BookBean> title_like = BookDAO.SelectLikeTitle(sc.nextLine());
                            if (!title_like.isEmpty()) {
                                for (BookBean rs : title_like) {
                                    System.out.println("ID        ：" + rs.getId());
                                    System.out.println("本のタイトル：" + rs.getTitle());
                                    System.out.println("著者       ：" + rs.getAuthor());
                                    System.out.println("ーーー");
                                }
                            }else {
                                System.out.println("該当するタイトル名はありません");
                            }
                        } else if (kensaku.equals("9")||kensaku.equals("９")) {
                            System.out.print("著者のワードを入力➡");
                            ArrayList<BookBean> author_like = BookDAO.SelectLikeName(sc.nextLine());
                            if (!author_like.isEmpty()) {
                                for (BookBean rs : author_like) {
                                    System.out.println("ID        ：" + rs.getId());
                                    System.out.println("本のタイトル：" + rs.getTitle());
                                    System.out.println("著者       ：" + rs.getAuthor());
                                    System.out.println("ーーー");
                                }
                            }else {
                                System.out.println("該当する著者名はありません");
                            }
                        } else if(kensaku.equals("1")||kensaku.equals("１")){
                            try {
                                System.out.print("IDを入力➡");
                                int select = Integer.parseInt(sc.nextLine());
                                BookBean select_id = BookDAO.SelectById(select);
                                if (select_id != null) {
                                    System.out.println("ID        ：" + select_id.getId());
                                    System.out.println("本のタイトル：" + select_id.getTitle());
                                    System.out.println("著者       ：" + select_id.getAuthor());
                                    System.out.println("ーーー");
                                } else {
                                    System.out.println("該当するIDはありません");
                                }
                            }catch (NumberFormatException e){
                                System.out.println("数地を入力してください");
                            }
                        }else{
                            System.out.println("操作画面にもどります");
                            break;
                        }
                        //追加
                    }else if (n.equals("3")||n.equals("３")) {
                            System.out.println("こちらは本の追加ですがあっていますか？");
                            int m = 1;
                            while (m == 1) {
                                System.out.println("本の追加をする場合は9を、本の追加を行わない場合は１を入力してください");
                                String k = sc.nextLine();
                                if (k.equals("9")||k.equals("９")) {
                                    try {
                                        System.out.println("id,タイトル,著者を入力します");
                                        System.out.println("idを入力⇓");
                                        int id = Integer.parseInt(sc.nextLine());
                                        BookBean select_id = BookDAO.SelectById(id);

                                        if (select_id != null) {
                                            System.out.println("IDが重複しています。他のidを入力してください");
                                            System.out.println("追加選択画面に戻ります");
                                            continue;
                                        }
                                        System.out.println("タイトルを入力⇓");
                                        String title = sc.nextLine();
                                        System.out.println("著者を入力⇓");
                                        String author = sc.nextLine();
                                        BookBean insert_Book = BookDAO.insertBooks(id, title, author);
                                        System.out.println("本の追加が完了しました");
                                    }catch(NumberFormatException e){
                                        System.out.println("数値を入力してください");
                                    }
                                } else if (k.equals("1")||k.equals("１")) {
                                    m = 2;
                                } else {
                                    System.out.println("9か1を入力してください");
                                }
                            }
                    }
                    //削除
                    else if (n.equals("4")||n.equals("４")) {
                        System.out.println("削除するidを入れてください");
                        try{
                            int delete = Integer.parseInt(sc.nextLine());
                            if(BookDAO.SelectById(delete) != null){
                                BookBean Delete = BookDAO.Delete_Book(delete);
                                System.out.println("削除を完了しました");
                            }else{
                                System.out.println("IDが見つかりません");
                                System.out.println("操作画面に戻ります");
                                j=2;
                                continue;
                            }
                        }catch (NumberFormatException e){
                            System.out.println("数値を入力してください");
                        }
                    }
                    j = 2;
                }
            }
            break;
        }
    }
}

    class OriginalException extends Exception {
        public OriginalException(String message) {
            super(message);
        }
    }

