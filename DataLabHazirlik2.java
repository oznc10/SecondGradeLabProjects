public class DataLabHazirlik2<E> {
    private  class Node<E>{
        private E element;
        private Node<E> next;
        public Node(E e,Node<E> n){
            this.element = e;
            this.next = n;
        }
        public E getElement(){
            return this.element;
        }
        public Node<E> getNext(){
            return this.next;
        }
        public void setNext(Node<E> n){
            this.next = n;
        }
        @Override
        public String toString(){
            return element.toString();
        }
    }
    private Node<E> head;
    private Node<E> tail;
    private int size;
    public DataLabHazirlik2(){
        head = null; // bunların eski halinde niye yanlış olduğunu anlamadım
        tail = null;
        size = 0;
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
       return size == 0;
    }
    public E first(){
        if(isEmpty()){
            return null;
        }
        return head.getElement();
    }
    public E last(){
        if(isEmpty()){
            return null;
        }
        return tail.getElement();
    }
    public void addFirst(E e){
        Node<E> newest = new Node<>(e,head);
        head = newest;
        if(isEmpty()){
            tail = head;
        }
        size++;
    }
    public void addLast(E e){
        Node<E> newest = new Node<>(e,null);
        if(isEmpty()){
            head = newest;
            tail = newest;
        }
        else{
            tail.setNext(newest);
            tail = newest;
        }
        size++;
    }
    public E removeFirst(){
        if(isEmpty()){
            return null;
        }
        E removed = head.getElement();
        head = head.getNext();
        size--;
        if(isEmpty()){
            tail = null;
        }
        return removed;
    }
    public E removeLast(){
        if(isEmpty()){
            return null;
        }
        if(head.getNext().equals(null)){
            E element = head.getElement();
            head = null;
            tail = null;
            size--;
            return element;
        }
        E removed = tail.getElement();
        Node<E> newTail = head;
        while(newTail.getNext() != tail){
            newTail = newTail.getNext();
        }
        newTail.setNext(null);
        tail = newTail;
        size--;
        return removed;
    }
    void printList(){
        if(isEmpty()){
            System.out.println("Empty list.");
            return;
        }
        Node<E> current = head;
        int count = 0;
        while(current != null){
            System.out.println((count + 1) + " - " + current.getElement().toString());
            current = current.getNext();
            count++;
        }
        System.out.println("Print process completed.There are " + count + " book in library.");
    }
      public static void main(String[] args) {

        // --- BOOK LİSTESİ TESTİ ---
        DataLabHazirlik2<Book> bookList = new DataLabHazirlik2<>();

        Book b1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        Book b2 = new Book("1984", "George Orwell", 1949);
        Book b3 = new Book("To Kill a Mockingbird", "Harper Lee", 1960);

        bookList.addLast(b1);
        bookList.addLast(b2);
        bookList.addLast(b3);

        System.out.println("----- BOOK LIST -----");
        bookList.printList();

        System.out.println("First Book: " + bookList.first());
        System.out.println("Last Book: " + bookList.last());

        bookList.removeFirst();
        System.out.println("After removing first book:");
        bookList.printList();

        bookList.removeLast();
        System.out.println("After removing last book:");
        bookList.printList();

        // --- MOVIE LİSTESİ TESTİ ---
        DataLabHazirlik2<Movie> movieList = new DataLabHazirlik2<>();

        Movie m1 = new Movie("Inception", "Christopher Nolan", 2010);
        Movie m2 = new Movie("The Matrix", "Wachowski Sisters", 1999);
        Movie m3 = new Movie("Interstellar", "Christopher Nolan", 2014);

        movieList.addFirst(m1);
        movieList.addFirst(m2);
        movieList.addFirst(m3);

        System.out.println("\n----- MOVIE LIST -----");
        movieList.printList();

        System.out.println("First Movie: " + movieList.first());
        System.out.println("Last Movie: " + movieList.last());
    }
}
class Book{
        private String bookName;
        private String author;
        private int year;
        public Book(String bookName,String author,int year){
            this.bookName = bookName;
            this.author = author;
            this.year = year;
        }
        public String getBookName(){
            return this.bookName;
        }
        public String getAuthor(){
            return this.author;
        }
        public int getYear(){
            return year;
        }
        @Override
        public String toString(){
            return "Book: " + bookName + ", Author: " + author + ", Year: " + year;
        }
    }
    class Movie {
    private String movieName;
    private String director;
    private int year;

    public Movie(String movieName, String director, int year) {
        this.movieName = movieName;
        this.director = director;
        this.year = year;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getDirector() {
        return this.director;
    }

    public int getYear() {
        return this.year;
    }

    @Override
    public String toString() {
        return "Movie: " + movieName + ", Director: " + director + ", Year: " + year;
    }
}

