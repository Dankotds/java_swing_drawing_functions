package com.company.Controllers;

public class HeapSort {
    public void pyramidSort(int arrayToSort[]) {
        int n = arrayToSort.length;

        // Построение кучи (перегруппируем массив)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arrayToSort, n, i);

        for (int i=n-1; i>=0; i--) {
            int temp = arrayToSort[0];
            arrayToSort[0] = arrayToSort[i];
            arrayToSort[i] = temp;

            // Вызываем процедуру heapify на уменьшенной куче
            heapify(arrayToSort, i, 0);
        }

    }

    // Процедура для преобразования в двоичную кучу поддерева с корневым узлом i, что является
        // индексом в arrayToSort[]. n - размер кучи
    private void heapify(int arrayToSort[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && arrayToSort[l] > arrayToSort[largest])
            largest = l;

        if (r < n && arrayToSort[r] > arrayToSort[largest])
            largest = r;

        if (largest != i) {
            int swap = arrayToSort[i];
            arrayToSort[i] = arrayToSort[largest];
            arrayToSort[largest] = swap;

            heapify(arrayToSort, n, largest);
        }
    }
}