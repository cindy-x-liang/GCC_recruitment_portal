package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSeqTest {

    // Helper functions for creating lists used by multiple tests.  By constructing strings with
    // `new`, more likely to catch inadvertent use of `==` instead of `.equals()`.

    /**
     * Creates [].
     */
    static Seq<String> makeList0() {
        return new LinkedSeq<>();
    }

    /**
     * Creates ["A"].  Only uses prepend.
     */
    static Seq<String> makeList1() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B"].  Only uses prepend.
     */
    static Seq<String> makeList2() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "C"].  Only uses prepend.
     */
    static Seq<String> makeList3() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "A"].  Only uses prepend.
     */
    static Seq<String> makeList4() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }


    /**
     * Creates ["A", "B", "C","D"].  Only uses prepend.
     */
    static Seq<String> makeList5() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("D"));
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates a list containing the same elements (in the same order) as array `elements`.  Only
     * uses prepend.
     */
    static <T> Seq<T> makeList(T[] elements) {
        Seq<T> ans = new LinkedSeq<>();
        for (int i = elements.length; i > 0; i--) {
            ans.prepend(elements[i - 1]);
        }
        return ans;
    }

    @Test
    void testConstructorSize() {
        Seq<String> list = new LinkedSeq<>();
        assertEquals(0, list.size());
    }

    @Test
    void testPrependSize() {
        // List creation helper functions use prepend.
        Seq<String> list;

        list = makeList1();
        assertEquals(1, list.size());

        list = makeList2();
        assertEquals(2, list.size());

        list = makeList3();
        assertEquals(3, list.size());
    }

    @Test
    void testToString() {
        Seq<String> list;

        list = makeList0();
        assertEquals("[]", list.toString());

        list = makeList1();
        assertEquals("[A]", list.toString());

        list = makeList2();
        assertEquals("[A, B]", list.toString());

        list = makeList3();
        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    void testContains() {
        Seq<String> list;

        //one A
        list = makeList2();
        assertTrue(list.contains("A"));

        //no C
        list = makeList2();
        assertFalse(list.contains("C"));

        //repeats of A
        list = makeList4();
        assertTrue(list.contains("A"));
    }

    @Test
    void testGet() {
        Seq<String> list;

        list = makeList2();
        assertEquals("A",list.get(0));

        list = makeList2();
        assertEquals("B",list.get(1));

        list = makeList4();
        assertEquals("A",list.get(2));
    }

    @Test
    void testAppend(){
        Seq<String> list;

        list = makeList1();
        list.append("B");
        assertEquals(makeList2(),list);


        list.append("C");
        assertEquals(makeList3(),list);

        list.append("D");
        assertEquals(makeList5(),list);


    }

    @Test
    void testInsertBefore(){
        Seq<String> list;
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend("B");

        list = ans;
        list.insertBefore("A","B");
        assertEquals(makeList2(),list);

        list.insertBefore("C","B");
        String[] newList = {"A","C","B"};
        assertEquals((makeList(newList)),list);

        list.insertBefore("D","B");
        String[] newList2 = {"A","C","D","B"};
        assertEquals(makeList(newList2),list);
    }


    @Test
    void testRemove(){
        Seq<String> list;

        list = makeList5();
        assertTrue(list.remove("D"));

        list.append("C");
        assertTrue(list.remove("C"));

        assertFalse(list.remove("D"));
    }

    @Test
    void testEquals(){
        Seq<String> list;

        list = makeList1();
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend("A");
        assertTrue(ans.equals(list));

        list = makeList2();
        ans.append("B");
        assertTrue(ans.equals(list));

        list = makeList3();
        ans.append("C");
        assertTrue(ans.equals(list));

        assertFalse(ans.equals(makeList4()));

    }


    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered `hashCode()` or `assertThrows()` in class.
     */

    @Test
    void testHashCode() {
        assertEquals(makeList0().hashCode(), makeList0().hashCode());

        assertEquals(makeList1().hashCode(), makeList1().hashCode());

        assertEquals(makeList2().hashCode(), makeList2().hashCode());

        assertEquals(makeList3().hashCode(), makeList3().hashCode());
    }

    @Test
    void testIterator() {
        Seq<String> list;
        Iterator<String> it;

        list = makeList0();
        it = list.iterator();
        assertFalse(it.hasNext());
        Iterator<String> itAlias = it;
        assertThrows(NoSuchElementException.class, () -> itAlias.next());

        list = makeList1();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());

        list = makeList2();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertFalse(it.hasNext());
    }
}
