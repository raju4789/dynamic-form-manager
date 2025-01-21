import { useCallback } from 'react';
import log from '../logger';

const useLocalStorage = <T>(key: string) => {
  /**
   * Save an item to localStorage
   * @param value - The value to store (must be serializable)
   */
  const setItem = useCallback((value: T): void => {
    try {
      const serializedValue = JSON.stringify(value);
      localStorage.setItem(key, serializedValue);
    } catch (error) {
      log.error(`Error saving key "${key}" to localStorage:`, error);
    }
  }, [key]);

  /**
   * Retrieve an item from localStorage
   * @returns The parsed value or null if not found
   */
  const getItem = useCallback((): T | null => {
    try {
      const item = localStorage.getItem(key);
      return item ? (JSON.parse(item) as T) : null;
    } catch (error) {
      log.error(`Error reading key "${key}" from localStorage:`, error);
      return null;
    }
  }, [key]);

  /**
   * Remove an item from localStorage
   */
  const removeItem = useCallback((): void => {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      log.error(`Error removing key "${key}" from localStorage:`, error);
    }
  }, [key]);

  /**
   * Clear all items from localStorage
   */
  const clear = useCallback((): void => {
    try {
      localStorage.clear();
    } catch (error) {
      log.error('Error clearing localStorage:', error);
    }
  }, []);

  return {
    setItem,
    getItem,
    removeItem,
    clear,
  };
};

export default useLocalStorage;