package home3;
import java.util.*;


class MyArrayList<E> implements List<E> {
    private int size;
    private int capacity;
    private final int DEFAULT_CAPACITY = 16;
    private Object[] array;

    public MyArrayList() {
        size = 0;
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    public MyArrayList(int newCapacity) {
        size = 0;
        capacity = newCapacity;
        array = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(Object elem) {
        return indexOf(elem) >= 0;
    }

    private void enlargeCapacity() {
        capacity = capacity + (capacity / 2);
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public boolean add(Object elem) {
        if (size + 1 > capacity) {
            enlargeCapacity();
        }
        array[size] = elem;
        size++;
        return true;
    }

    @Override
    public void add(int index, Object elem) {
        if (index < 0) {
            return;
        }
        if (size + 1 > capacity) {
            enlargeCapacity();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = elem;
        size++;
    }

    @Override
    public boolean addAll(Collection c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (Object elem : c.toArray()) {
            add(elem);
        }
        return true;
   }

   @Override
   public boolean addAll(int index, Collection c) {
       if (c == null || c.isEmpty() || index < 0) {
           return false;
       }
       for (Object elem : c.toArray()) {
           add(index++, elem);
       }
       return true;
   }

   @Override
   public Object set(int index, Object elem) {
       if (index < 0) {
           return null;
       }
       Object o = array[index];
       array[index] = elem;
       return o;
   }


   @Override
   public E get(int index) {
       if (index < 0) {
           return null;
       }
       return (E) array[index];
   }

   private void shiftToLeft(int index) {
       size--;
       if (size != index) {
           System.arraycopy(array, index + 1, array, index, size - index);
       }
       array[size] = null;
   }

   @Override
   public E remove(int index) {
       Object elem = null;
       elem = get(index);
       shiftToLeft(index);
       return (E) elem;
   }

   @Override
   public boolean remove(Object elem) {
       if (size == 0) {
           return false;
       }
       int i;
       for (i = 0; i < size; i++) {
           if (array[i] == null && elem == null) {
               break;
           }
           if (array[i] != null && array[i].equals(elem)) {
               break;
           }
       }
       if (i < size) {
           shiftToLeft(i);
           return true;
       }
       return false;
   }

   @Override
   public boolean removeAll(Collection c) {
       if (c == null || c.size() == 0 || size == 0) {
           return false;
       }
       boolean answer = false;
       int i = 0;
       while (i < size) {
           if (c.contains(array[i])) {
               shiftToLeft(i);
               answer = true;
           } else {
               i++;
           }
       }
       return answer;
   }

   @Override
   public List subList(int fromIndex, int toIndex) {
       if (fromIndex > toIndex) {
           int tmp = fromIndex;
           fromIndex = toIndex;
           toIndex = tmp;
       }
       if (fromIndex < 0 || toIndex > size) {
           return null;
       }
       List my = new MyArrayList(toIndex - fromIndex);
       for (int i = fromIndex; i < toIndex; i++) {
           my.add(array[i]);
       }
       return my;
   }

   @Override
   public Iterator iterator() {
       return null;
   }

   @Override
   public ListIterator listIterator() {
       return null;
   }

   @Override
   public ListIterator listIterator(int index) {
       return null;
   }

   @Override
   public int indexOf(Object elem) {
       if (elem == null) {
           for (int i = 0; i < size; i++) {
               if (array[i] == null) {
                   return i;
               }
           }
       } else {
           for (int i = 0; i < size; i++) {
               if (elem.equals(array[i])) {
                   return i;
               }
           }
       }
       return -1;
   }

   @Override
   public int lastIndexOf(Object elem) {
       int lastIndex = -1;
       if (elem == null) {
           for (int i = 0; i < size; i++) {
               if (array[i] == null) {
                   lastIndex = i;
               }
           }
           return lastIndex;
       } else {
           for (int i = 0; i < size; i++) {
               if (elem.equals(array[i])) {
                   lastIndex = i;
               }
           }
       }
       return lastIndex;
   }

   @Override
   public boolean retainAll(Collection c) {
       if (c == null) {
           return true;
       }
       if (c.size() == 0) {
           clear();
           return true;
       }
       int i = 0;
       boolean answer = false;
       while (i < size) {
           if (c.contains(array[i])) {
               i++;
           } else {
               shiftToLeft(i);
               answer = true;
           }
       }
       return answer;
   }

   @Override
   public boolean containsAll(Collection c) {
       if (c == null) {
           return false;
       }
       if (c.size() == 0) {
           return true;
       }
       for (Object elem : c.toArray()) {
           if (!contains(elem)) {
               return false;
           }
       }
       return true;
   }

   @Override
   public Object[] toArray() {
       Object[] newArray = new Object[size];
       System.arraycopy(array, 0, newArray, 0, size);
       return newArray;
   }

   @Override
   public <T> T[] toArray(T[] a) {
       if (a.length < size) {
           return (T[]) Arrays.copyOf(array, size, a.getClass());
       }
       System.arraycopy(array, 0, a, 0, size);
       if (a.length > size) {
           a[size] = null;
       }
       return a;
   }
}

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> arrayTest = new MyArrayList<String>(2);
        MyArrayList<String> newArrayTest = new MyArrayList<String>(3);

        System.out.print("Check add(elem) and add(index, elem). Array: ");
        arrayTest.add("Apple");
        arrayTest.add("Banana");
        arrayTest.add(1, "Avocado");
        Object[] objects = arrayTest.toArray();
        for (Object elem : objects) {
            System.out.print(elem + " ");
        }
        System.out.println();


        System.out.print("Check add(elem) and add(index, elem). NewArray: ");
        newArrayTest.add("Date");
        newArrayTest.add("Grapes");
        newArrayTest.add("Grapefruit");
        newArrayTest.add(1, "Fig");
        objects = newArrayTest.toArray();
        for (Object elem : objects) {
            System.out.print(elem + " ");
        }
        System.out.println();


        System.out.println("Check array.isEmpty(): " + arrayTest.isEmpty());
        System.out.println("Check array.size() and newArray.size(): " + arrayTest.size() + " " + newArrayTest.size());
        System.out.println("Check array.contains(Apple) and array.contains(Kiwi): " + arrayTest.contains("Apple") + " " + arrayTest.contains("Kiwi"));


        System.out.print("Check remove(0) and remove(Banana). Array: ");
        arrayTest.remove(0);
        arrayTest.remove("Banana");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();

        arrayTest.add(0, "Apple");
        arrayTest.add("Apple");
        arrayTest.set(2, "Banana");
        arrayTest.add("Avocado");
        System.out.print("Check add(elem) and set(2, Banana). Array: ");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();


        System.out.print("Check array.subList(1, 4). Sublist: ");
        List subList = arrayTest.subList(1, 4);
        for (int i = 0; i < subList.size(); i++) {
            System.out.print(subList.get(i) + " ");
        }
        System.out.println();


        arrayTest.addAll(newArrayTest);
        System.out.print("Check array.addAll(newArray). Array: ");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();


        arrayTest.addAll(4, newArrayTest);
        System.out.print("Check array.addAll(4, newArray). Array: ");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();


        System.out.println("Check indexOf(Avocado) and lastIndexOf(Avocado): " +
                          arrayTest.indexOf("Avocado") + " " + arrayTest.lastIndexOf("Avocado"));
        System.out.println("Check array.containsAll(newArrayTest): " + arrayTest.containsAll(newArrayTest));


        arrayTest.removeAll(newArrayTest);
        System.out.print("Check array.removeAll(newArray): ");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();


        arrayTest.retainAll(subList);
        System.out.print("Check array.retainAll(subList): ");
        for (int i = 0; i < arrayTest.size(); i++) {
            System.out.print(arrayTest.get(i) + " ");
        }
        System.out.println();

        arrayTest.clear();
        newArrayTest.clear();
        System.out.println("Size of arrays after clear(): " + arrayTest.size() + " " + newArrayTest.size());
    }
}
