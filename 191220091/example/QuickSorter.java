package example;

public class QuickSorter implements Sorter {

    private int[] a;

    @Override
    public void load(int[] a) {
        this.a = a;
    }

    private void swap(int i, int j) {
        int temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }

    private String plan = "";

    public void quickSort(int low,int high)
    {
        int i,j,temp;
        if(low>high)
        {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = a[low];
 
        while (i<j) 
        {
            //先看右边，依次往左递减
            while (temp<=a[j] && i<j) 
            {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=a[i] && i<j) 
            {
                i++;
            }
            //如果满足条件则交换
            if (i<j) 
            {
               swap(i, j);
            }
 
        }
        //最后将基准为与i和j相等位置的数字交换
        // a[low] = a[i];
         //a[i] = temp;
        swap(low, i);
        //递归调用左半数组
        quickSort(low, j-1);
        //递归调用右半数组
        quickSort(j+1, high);
    }

    @Override
    public void sort() 
    {   
        int low = 0;
        int high = this.a.length - 1;
        quickSort(low, high);
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}
