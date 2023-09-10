package assn07;

import java.util.*;
import java.lang.Math;

public class PasswordManager<K,V> implements Map<K,V> {
    private static final String MASTER_PASSWORD = "password123";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }


    // TODO: put
    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        if (_passwords[index] == null) {
            _passwords[index] = new Account<>(key, value);
        } else {
            Account<K, V> current = _passwords[index];
            while (current.getNext() != null) {
                if (current.getWebsite().equals(key)) {
                    current.setPassword(value);
                    return;
                }
                current = current.getNext();
            }
            if (current.getWebsite().equals(key)) {
                current.setPassword(value);
                return;
            }
            current.setNext(new Account<>(key, value));
        }
    }

    // TODO: get
    @Override
    public V get(K key) {
        int index = getIndex(key);
        Account<K, V> current = _passwords[index];
        while (current != null) {
            if (current.getWebsite().equals(key)) {
                return current.getPassword();
            }
            current = current.getNext();
        }
        return null;
    }

    // TODO: size
    @Override
    public int size() {
        int size = 0;
        for (Account<K, V> password : _passwords) {
            while (password != null) {
                size++;
                password = password.getNext();
            }
        }
        return size;
    }

    // TODO: keySet
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Account<K, V> password : _passwords) {
            while (password != null) {
                set.add(password.getWebsite());
                password = password.getNext();
            }
        }
        return set;
    }

    // TODO: remove
    @Override
    public V remove(K key) {
        int index = getIndex(key);
        Account<K, V> current = _passwords[index];
        Account<K, V> previous = null;
        while (current != null) {
            if (current.getWebsite().equals(key)) {
                if (previous == null) {
                    _passwords[index] = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                return current.getPassword();
            }
            previous = current;
            current = current.getNext();
        }
        return null;
    }

    // TODO: checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
        List<K> list = new ArrayList<>();
        for (Account<K, V> password : _passwords) {
            while (password != null) {
                if (password.getPassword().equals(value)) {
                    list.add(password.getWebsite());
                }
                password = password.getNext();
            }
        }
        return list;
    }

    // TODO: checkMasterPassword
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
        return Objects.equals(enteredPassword, MASTER_PASSWORD);
    }
    /*
    Generates random password of input length
     */
    @Override
    public String generateRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    private int getIndex(K key) {
        int code = key.hashCode();
        return Math.abs(code % 50);
    }

    /*
    Used for testing, do not change
     */
    public Account[] getPasswords() {
        return _passwords;
    }
}
