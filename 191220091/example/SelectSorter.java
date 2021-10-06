package example;

public class SelectSorter implements Sorter
{
    private int[] a;

    @Override
    public void load(int[] a) 
    {
        this.a = a;
    }

    private void swap(int i, int j) 
    {
        int temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {//选择排序
        for(int i = 0; i < a.length-1; i++)
        {
            int min = i;
            for(int j = i+1; j < a.length; j++)
            {
                if(a[j] < a[min])
                {
                    min = j;
                }
            }
            if(min!=i)
            {
                swap(i, min);
            }
        }
    }
    

    @Override
    public String getPlan() 
    {
        return this.plan;
    }
}