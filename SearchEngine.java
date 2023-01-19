import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> words = new ArrayList<>(); 

    public String handleRequest(URI url) {
        //ArrayList<String> words = new ArrayList<>(); 
        if (url.getPath().equals("/")){
            return words.toString();
        }
        else if(url.getPath().contains("/add")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                words.add(parameters[1]);
                return words.toString();
            }
        }
        else if(url.getPath().contains("/search")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                ArrayList<String> searched = new ArrayList<>();
                for(String word:words){
                    if(word.contains(parameters[1])){
                        searched.add(word);
                    }
                }
                if(searched.size() == 0){
                    return "Nothing found";
                }
                else{
                    return String.format("your words: %s", searched); 
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
