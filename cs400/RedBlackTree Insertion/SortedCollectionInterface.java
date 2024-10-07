// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: N/A
// Lecturer: Gary Dahl
// Notes to Grader: none
public interface SortedCollectionInterface<T extends Comparable<T>>
{
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    public boolean remove(T data) throws NullPointerException, IllegalArgumentException;

    public boolean contains(T data);

    public int size();

    public boolean isEmpty();
}