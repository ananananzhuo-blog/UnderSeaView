> ### 欢迎关注我的公众号，学习更多知识
>
> 读一本好书。 **——我说的**

关注公众号学习更多知识

![](https://files.mdnice.com/user/15648/404c2ab2-9a89-40cf-ba1c-02df017a4ae8.jpg)


前面几篇文章我们分别讲了自定义控件中常用到的 Canvas、Paint、Path。本篇我们在前面几篇的基础上更进一步，绘制一篇海底世界的效果。


## 绘制背景

首先大海应该是蓝色的吧，而且应该是一个渐变色，那么根据 paint 篇的内容，我们便可以使用 paint.setShader 来实现，实现思路就是使用 Canvas.drawRect 方法绘制整个画布，画笔 Paint 设置一个 Shader，代码如下：

```
paintBack.shader = LinearGradient(
            measuredWidth / 3f,
            0f,
            measuredWidth * 2 / 3f,
            measuredHeight.toFloat(),
            Color.WHITE,
            Color.parseColor("#000055"),
            Shader.TileMode.CLAMP
        )
```

```
canvas.drawRect(background, paintBack)
```

效果大概这个样子

![](https://files.mdnice.com/user/15648/1a2708ee-be4b-4491-9d80-a8b1605fe8cf.png)

## 绘制鲸鱼

咳咳，鲸鱼这个东西我在百度上面找了一张图片

![](https://files.mdnice.com/user/15648/06147fb5-64e9-4844-8a9c-7bfcc17adfd9.jpeg)

而后使用 ps 抠出了其中最漂亮的大蓝鲸，并且使用 ps 液化工具对蓝鲸的尾巴进行了处理，目的就是让蓝鲸在运动的过程中尾巴不停的摆动从而实现游泳的效果

![](https://files.mdnice.com/user/15648/0be48031-ef74-4522-90c0-7fa05b028798.png)
![](https://files.mdnice.com/user/15648/03768083-daaa-43e2-96f0-c109181ddb7a.png)
![](https://files.mdnice.com/user/15648/be6ee39e-dafa-4783-9275-b3f92ff3a232.png)
![](https://files.mdnice.com/user/15648/08388d9b-7e16-44e4-8eee-9509d1b95444.png)

###### 代码：

```
 private val bitmaps = listOf<Bitmap>(
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish1),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish2),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish3),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish4),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish3),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish2),
        BitmapFactory.decodeResource(context.resources, R.drawable.bluefish1),
    )
```

```
 canvas.run {
            drawBitmap(bitmaps[bitmapIndex % bitmaps.size], 100f, 100f, paint)
            bitmapIndex++
            drawFishWithPath()
        }
```

###### 实现效果：

![](https://files.mdnice.com/user/15648/39d41729-ee28-4654-8bef-a9dbb2f0e04f.gif)

虎鲸光能摆动尾巴还是不够的，我们还应有让他能做游泳的动作，最好能按照我们指定的路径来运动是最好的了
###### 定义路径的核心代码

```
 fishPath.moveTo(100f, 100f)
        for (i in 0..20) {
            fishPath.apply {
                cubicTo(randowX(), randowY(), randowX(), randowY(), randowX(), randowY())
            }
        }
        fishPath.close()
```

###### 画出路径效果
![](https://files.mdnice.com/user/15648/d246bf51-3632-4e43-aa96-9ed691dcd3fc.png)

这里是有问题的，因为路径转向的时候有很多锐角，可想而知这样是不行的，虎鲸宝宝也不会这么游泳的对吧，这个后续再优化，目前重要的是实现效果
## 让鱼沿着路径动起来

这里要借助PathMeasure工具了


```
dstPath.reset()
        var stop = start+100f
        pathMeasure.getSegment(start, stop, dstPath, true)
        val matrix =  Matrix()
        pathMeasure.getMatrix(stop, matrix, (PathMeasure.POSITION_MATRIX_FLAG.or(PathMeasure.TANGENT_MATRIX_FLAG)))
        val bitmap = bitmaps[bitmapIndex % bitmaps.size]
        matrix.preTranslate(-bitmap.width / 2f, -bitmap.height / 2f)
        canvas.drawBitmap(bitmap, matrix, paint)
```
代码是通过pathMeasure.getSegment方法可以截取path，从而在path的某个位置绘制鲸鱼图片，因为能拿到path某个点的正切值，所以我们的鲸鱼可以始终做到沿着切线游泳

游泳效果：

悲剧的是，中间一时忘记录制屏幕，只能把最终效果图放上来了


![](https://files.mdnice.com/user/15648/70e43c62-4a06-493c-a187-bdcfe2a7b8b7.gif)

## 画太阳和气泡
既然最终效果已经出来了，那么后面直接放画太阳和气泡的代码吧

###### 画太阳

画太阳主要的点在于画周围的阳光。画阳光的时候我们将太阳弧度20等分，然后从两个等分点之间向外画三角就能实现我们的阳光效果了。

```
path.moveTo(radius + sunX, sunY)
        val degree = 3.14f * 2 / leafNum
        for (i in 1..leafNum) {
            val x1 = radius * cos(i * degree) + sunX
            val y1 = radius * sin(i * degree) + sunY

            val halfDegree = (i - 0.5) * degree
            val shineRadius = radius + Random.nextInt(50)
            val controllX = shineRadius * cos(halfDegree).toFloat() + sunX
            val controllY =  shineRadius * sin(halfDegree).toFloat() + sunY
            path.lineTo(controllX, controllY)
            path.lineTo(x1, y1)
        }
        path.close()
```


###### 画气泡

我们使用RadiaGradient镜像渐变的方式实现气泡效果，要注意的点是镜像渐变的颜色中间点不要选在圆心，否则会很难看

```
paint.shader=RadialGradient(cycleX+40,cycleY-40,radius+300, Color.WHITE,Color.GREEN,Shader.TileMode.CLAMP)
        canvas.drawCircle(cycleX, cycleY, radius, paint)
```



