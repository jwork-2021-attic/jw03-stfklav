# W03 191220091

### 一. 对代码工作原理的理解

> ##### 隐写术图编码

类`SteganographyFactory`用来将编译得到的类字节码放入指定图片中生成隐写术图。

```java
public static void getSteganography(String classSource, String originImage) throws IOException 
{
        String className = classSource.substring(0, classSource.indexOf(".")).replace("/", ".");
    	//编译源文件得到字节码
        SteganographyFactory.compile(classSource);
    	//读源图片
        BufferedImage image = ImageIO.read(new File(originImage));
    	//把字节码编码进图片
        SteganographyEncoder steganographyEncoder = new SteganographyEncoder(image);
        BufferedImage encodedImage = steganographyEncoder.encodeFile(new File(classSource.replace("java", "class")));
    	//生成指定名称的隐写术图
        ImageIO.write(encodedImage, "png", new File(className+".png"));

    }
```



> ##### 类加载器

自定义类加载器`SteganographyClassLoader`继承自类`ClassLoader`，默认设置的父类加载器`parentClassLoader`是`AppClassLoader`，`ClassLoader`加载类的顺序如下：

1. 调用`findLoadedClass(String) `来检查是否已经加载类；

2. 在父类加载器上调用`loadClass`方法，如果父亲不能加载，一次一级一级传给子类；

3. 调用子类`findClass(String) `方法查找类。若还加载不了就返回`ClassNotFoundException`，不交给发起请求的加载器的子加载器。

   `ClassLoader`的`loadClass`方法部分代码如下：

   ```java
   protected Class<?> loadClass(String name, boolean resolve)
           throws ClassNotFoundException
       {
       	//这里根据当前类加载器是否具备并行能力而获取对应的锁对象
           synchronized (getClassLoadingLock(name)) {
               /**
                * 在加载类之前先调用findLoadClass方法查找看该类是否加载过,
                * 若被加载过则直接返回该对象,无需二次加载,未加载过则返回null,
                * 进行下一个流程
                */
               Class c = findLoadedClass(name);
               //若该类未被加载过
               if (c == null) {
                   long t0 = System.nanoTime();
                   try {
                   	//如果父类加载器不为空,则调用父类加载器的loadClass方法加载
                       if (parent != null) {
                           c = parent.loadClass(name, false);
                       } else {
                       	//若父类加载器为空,则调用虚拟机的加载器Bootstrap ClassLoader来加载类
                           c = findBootstrapClassOrNull(name);
                       }
                   } catch (ClassNotFoundException e) {}
                   //若以上的操作都没有成功加载到类
                   if (c == null) {
                       long t1 = System.nanoTime();
                       //则调用该类自己的findClass方法来加载类
                       c = findClass(name);
                       // this is the defining class loader; record the stats
                       sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                       sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                       sun.misc.PerfCounter.getFindClasses().increment();
                   }
               }
               if (resolve) {
                   resolveClass(c);
               }
               return c;
           }
       }
   ```

一般来说自己开发的类加载器只需要覆写`findClass(String name)`方法即可，其他部分由`ClassLoader`中的`loadClass()`完成，不改变父类委托机制。

`SteganographyClassLoader`中覆写了`findClass(String name)`，让它根据得到图片的url读取隐写术图中暗含的要加载的类的字节码，创建相应的类并加载，返回对应的Class对象。

由于父类委托机制，如果保留着`BubbleSorter.class`等类的字节码，这些类就会由`SteganographyClassLoader`的父类加载器`AppClassLoader`加载，而如果删去这些文件，`AppClassLoader`无法加载相应类，就会交由`SteganographyClassLoader`读图片加载。

在`Scene.java`的`main`函数中，由类加载器加载好相应类返回Class类的对象后，就可根据这个Class对象创建一个对应类的对象实例	`sorter`，从而交由老头进行排序。



### 二. 隐写术图(在`W02`中实现的两个排序算法编码进自选图片得到)

#### `SelectSorter`:

https://user-images.githubusercontent.com/80143498/136231706-d85b59ba-44bd-43ba-900b-209f233f6326.png



#### `QuickSorter`:

https://user-images.githubusercontent.com/80143498/136231767-234034ab-2b6b-4be6-844e-e0029dc76b01.png




### 三.排序结果（动画）

#### `SelectSort`:

[![asciicast](https://asciinema.org/a/440393.svg)](https://asciinema.org/a/440393)



#### `QuickSort`:

[![asciicast](https://asciinema.org/a/440395.svg)](https://asciinema.org/a/440395)



### 四. 使用他人隐写术图排序

使用同学`191220057`林芳麒的隐写术图`S191220057.InsertSorter.png`，给`W02`中`example`的老头赋予排序能力并进行插入排序，得到了正确结果
使用部分代码：

![image](https://user-images.githubusercontent.com/80143498/136241676-25b314b3-b177-474c-b7bf-c2f377450093.png)
运行结果分步展示：

![image](https://user-images.githubusercontent.com/80143498/136241986-c13864e0-f25c-4630-a5f1-326a7ad5e62d.png)

