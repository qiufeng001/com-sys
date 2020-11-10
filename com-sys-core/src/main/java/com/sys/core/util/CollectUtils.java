package com.sys.core.util;

import org.apache.commons.lang.UnhandledException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 集合工具类
 *
 * @author z.h
 */
public final class CollectUtils {

    public static <E> List<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> List<E> newArrayList(int initialCapacity) {
        return new ArrayList<E>(initialCapacity);
    }

    public static <E> List<E> newArrayList(Collection<? extends E> c) {
        return new ArrayList<E>(c);
    }

    public static <E> List<E> newArrayList(E... values) {
        List<E> list = new ArrayList<E>(values.length);
        for (E e : values) {
            list.add(e);
        }
        return list;
    }

    /**
     * 将一个集合按照固定大小查分成若干个集合。
     *
     * @param list
     * @param count
     * @return
     */
    public static <T> List<List<T>> split(final List<T> list, final int count) {
        List<List<T>> subIdLists = CollectUtils.newArrayList();
        if (list.size() < count) {
            subIdLists.add(list);
        } else {
            int i = 0;
            while (i < list.size()) {
                int end = i + count;
                if (end > list.size()) {
                    end = list.size();
                }
                subIdLists.add(list.subList(i, end));
                i += count;
            }
        }
        return subIdLists;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection<?> coll) {
        return null != coll && !coll.isEmpty();
    }

    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }

    public static <E> Queue<E> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<E>();
    }

    public static <K, V> Map<K, V> newHashMap(Map<? extends K, ? extends V> m) {
        return new HashMap<K, V>(m);
    }

    public static <K, V> Map<K, V> newLinkedHashMap(Map<? extends K, ? extends V> m) {
        return new LinkedHashMap<K, V>(m);
    }

    public static <K, V> Map<K, V> newLinkedHashMap(int size) {
        return new LinkedHashMap<K, V>(size);
    }

    public static <E> Set<E> newHashSet() {
        return new HashSet<E>();
    }

    public static <E> Set<E> newHashSet(E... values) {
        Set<E> set = new HashSet<E>(values.length);
        for (E e : values) {
            set.add(e);
        }
        return set;
    }

    public static <E> Set<E> newHashSet(Collection<? extends E> c) {
        return new HashSet<E>(c);
    }

    public static Map<String, String> toMap(String[]... wordMappings) {
        Map<String, String> mappings = new HashMap<String, String>();
        for (int i = 0; i < wordMappings.length; i++) {
            String singular = wordMappings[i][0];
            String plural = wordMappings[i][1];
            mappings.put(singular, plural);
        }
        return mappings;
    }
    public static <T> List<T> intersection(List<T> first, List<T> second) {
        List<T> list = CollectUtils.newArrayList();
        Map<T, Integer> mapa = getCardinalityMap(first), mapb = getCardinalityMap(second);
        Set<T> elts = new HashSet<T>(first);
        elts.addAll(second);
        for (T obj : elts)
            for (int i = 0, m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++)
                list.add(obj);
        return list;
    }

    public static <T> List<T> union(List<T> first, List<T> second) {
        Map<T, Integer> mapa = getCardinalityMap(first), mapb = getCardinalityMap(second);
        Set<T> elts = new HashSet<T>(first);
        elts.addAll(second);
        List<T> list = newArrayList();
        for (T obj : elts)
            for (int i = 0, m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; i++)
                list.add(obj);
        return list;

    }

    public static <T> List<T> subtract(List<T> first, List<T> second) {
        List<T> list = newArrayList(first);
        for (T t : second)
            list.remove(t);
        return list;
    }

    public static <T> Map<T, Integer> getCardinalityMap(final List<T> coll) {
        Map<T, Integer> count = newHashMap();
        for (Iterator<T> it = coll.iterator(); it.hasNext();) {
            T obj = it.next();
            Integer c = (count.get(obj));
            if (c == null) count.put(obj, Integer.valueOf(1));
            else count.put(obj, new Integer(c.intValue() + 1));
        }
        return count;
    }

    private static final <T> int getFreq(final T obj, final Map<T, Integer> freqMap) {
        Integer count = freqMap.get(obj);
        return (count != null) ? count.intValue() : 0;
    }
}
