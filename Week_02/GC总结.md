### 测试环境
Mac OS，4核，16GB内存
分别测试不同GC在128M、256M、512M、1G、4G堆内存条件下的GC情况
压力测试使用wrk工具

### 串行GC
java -XX:+UseSerialGC -Xms512m -Xmx512m  -XX:+PrintGCDetails GCLogAnalysis 

#### 128M
前几次GC都发生在年轻代，GC时间基本都在10ms左右，年轻代使用率基本维持在11%，整个堆内存的使用量从7%逐渐上升68%，然后后面的GC都是FullGC，此时老年代的使用率已经达到99%，FullGC已经不能释放更多空闲的堆空间，直到应用出现OOM异常

#### 256M
同128M情况差不多，从前几次的youngGC，到后面的FullGC，直到出现
OOM异常

#### 512M
这次没有出现FullGC，每次youngGC的时间在50ms到70ms之间，old区的堆使用不断增长，程序结束时，old区占用达到91%

#### 1G
没有出现FullGC，而且GC次数减少，每次youngGC的时间在80ms到100ms之间，程序结束时，old区占用达到91%

#### 4G
没有出现GC，直到程序结束，eden区占用达到100%，整个new区的大小为1.2G

### 并行GC
java -XX:+UseParallelGC -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis 

#### 128M
前几次GC都发生在年轻代，GC时间基本都在10ms左右，年轻代使用率从13%增长到49%，整个堆内存的使用量从8%逐渐上升73%，然后后面的GC都是FullGC，年轻代使用率从0%增长到71.8%，此时老年代的使用率已经达到99%，FullGC已经不能释放更多空闲的堆空间，直到应用出现OOM异常

#### 256M
同128M情况差不多，9次youngGC之后都是FullGC，程序结束时，old区占用达到99%, eden区占有在88%

#### 512M
11次youngGC后出现1次FullGC，然后5次youngGC后出现1次FullGC，每次youngGC的时间在20ms到40ms之间，每次FullGC的时间在40ms到60ms之间，old区的堆使用不断增长，程序结束时，old区占用达到81%, eden区占有在4%

#### 1G
没有出现FullGC，而且GC次数减少，youngGC的时间在60ms逐渐增长170ms，程序结束时，old区占用达到46%，eden区占有在35%，s0区占有在99%

#### 4G
没有出现GC，直到程序结束，eden区占用达到85%，整个new区的大小为1.2G

### CMS GC
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m  -XX:+PrintGCDetails GCLogAnalysis 

#### 128M
前几次GC都发生在年轻代，GC时间基本都在10ms左右，年轻代使用率在11%，整个堆内存的使用量从13%逐渐上升42%，然后后面的GC都是CMS GC，年轻代使用率从0%增长到71.8%，此时老年代的使用率已经达到99%，CMS GC已经不能释放更多空闲的堆空间，直到应用出现OOM异常，eden区占有在100%，s0区占有在97%

#### 256M
同128M情况差不多，4次youngGC之后都是CMS GC，程序结束时，old区占用达到99%, eden区占有在84%

#### 512M
CMS GC次数减少，每次youngGC的时间在30ms到50ms之间，每次CMS-concurrent-preclean的时间在10ms，old区的堆使用不断增长，程序结束时，old区占用达到83%, eden区占有在39%

#### 1G
只出现一次CMS GC，youngGC的时间在50ms逐渐增长到90ms，程序结束时，old区占用达到49%，eden区占有在6%，s0区占有在99%

#### 4G
只出现三次youngGC，GC时间在90ms到130ms, 直到程序结束，eden区占用达到3%, old区占用在9%

### G1 GC
java -XX:+UseG1GC -Xms512m -Xmx512m  -XX:+PrintGC GCLogAnalysis 

#### 128M
region区大小1M，G1堆大小为131M，使用了273K，出现了OOM

#### 256M
出现了OOM，G1堆大小为262M，使用了273K，young区使用了1个region

#### 512M
没有出现OOM，young区使用了6个region，survivors占用4个region，g1堆占用65.8%

#### 1G
没有出现OOM，young区使用了348个region，survivors占用40个region，g1堆占用80.8%

#### 4G
没有出现OOM，young区使用了98个region，survivors占用16个region，g1堆占用16.9%

##### 小结
SerialGC是单线程GC，GC时必须暂停其他所有的工作线程，对于线上环境，不j建议使用串行GC
ParallerGC是多线程GC，可以提高吞吐量，高效利用CPU
CMS GC能够获得最短回收停顿时间，适合对延迟有高要求的系统
G1属于分代GC，兼顾年轻代和老年代，空间碎片较少

#### 压测
wrk -t20 -c20 -d60s http://localhost:8088/api/hello
不同Heap大小和GC类型下的吞吐量

| GC Type / Heap Size     | 128M  | 256M      | 512M      | 1G          | 4G         |
| ------------------  | ----  | ------- | ------- | ------- | ------- | 
| UseSerialGC                 | 16930  | 17844 | 19034 | 17607 | 12512 | 
| UseParallelGC               | 22000  | 15482 | 16857 | 17938 | 15253 | 
| UseConcMarkSweepGC  | 13197  | 13291 | 13890 | 14356 | 22757 | 
| UseG1GC                      | 14561  | 12854 | 16824 | 17168 | 14898 | 

##### 小结
随着内存的增大，几个GC都是一个先增后减的趋势，并不是内存越大性能越好



