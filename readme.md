# BottomNavigationFragment
BottomNavigationView+Fragment

这是一个BottomNavigationView+Fragment的示例

以show/hide的方式切换Fragment的显示与隐藏

解决了Activity因内存不足，导致被系统回收，Fragment出现重叠的问题

解决了Activity因屏幕旋转，导致Activity重建，导致Fragment出现重叠的问题

出现问题的原因：

Activity在内存不足时，会被系统回收，走Activity的onDestroy方法，可以在开发者选项中

打开不保留活动按钮，来模拟这种情形。当被系统回收时，Activity会走onSaveInstanceState方法，

在super.onSaveInstanceState(outState)方法中

    markFragmentsCreated();
    mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    Parcelable p = mFragments.saveAllState();
    if (p != null) {
    outState.putParcelable(FRAGMENTS_TAG, p);
    }

会保存Fragment的实例及状态

如果我们在onCreate方法中不做任何处理，当Activity因内存不足被系统回收再重新打开后，

supportFragmentManager中会保存有以前的Fragment实例，当再重新走onCreate方法我们会重新创建新的Fragment实例并添加到
事务中，就会出现Fragment重叠的问题

解决问题：
    
当重新走onCreate方法时，我们判断saveInstanceState是否为null,如果不为null，则从supportFragmentManager中取出原来的Fragment实例并缓存，如果找不到则重新创建。并在onSaveInstanceState方法执行时，保存我们最后选中的ItemId,在saveInstanceState方法中取出，并重新设置BottomNavigationView的默认选中条目id
    
具体实现详见代码