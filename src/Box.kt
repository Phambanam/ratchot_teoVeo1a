import com.sun.xml.internal.bind.v2.runtime.output.DOMOutput
import java.io.File
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color
import kotlin.math.abs
import kotlin.math.min

fun main(){
    task3(9996)
}



class Box (
    var red: Int,
    var white : Int,
    var black : Int,
    var green : Int,
    var blue : Int

        ){
    fun total() : Double{
    return (red + white + black+green+ blue).toDouble()

    }
}
fun parseData() : List<List<String>>{
    val file = File("src/task_1_ball_boxes_arrange.txt")
    var count = 0
    val list = mutableListOf<List<String>>()
    for(line in file.readLines())
        if(line.contains('#')){
            count++
            val tmp = line.replace(Regex("# [0-9]+, Balls:"),"").split(", ")
            list.add(tmp)

        }
    return list
}
fun task1(iterations : Int){
    val exp = parseData()
    val box1 = Box(20,62,43,59,86)
    val box2 = Box(15,13,83,80,89)
    val box3 = Box(39,32,70,48,21)
    val box4 = Box(19,47,72,40,52)
    val box5 = Box(93,12,69,41,75)
    val box6 = Box(19,81,41,30,69)
    val boxes = listOf(box1,box2,box3,box4,box5,box6)

    val firstBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val secondBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val thirdBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val fourthBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val fifthBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val sixthBox = listOf(1.0,1.0,1.0,1.0,1.0)

    val distributionBox = arrayListOf(firstBox,secondBox,thirdBox,fourthBox,fifthBox,sixthBox)
    var hypoProb: ArrayList<Double> = arrayListOf()
    for( i in 0 ..719) hypoProb.add(1.0/720)
    var sortedHypoProb = mutableMapOf<Int, Double>()
    val dataForGraph = mutableListOf<MutableList<Double>>()

    for(i in 0..10){
        dataForGraph.add(mutableListOf())
    }
    var k = 0
    for (i in 0 until iterations){
        distributionBox[i%6] = calcProb(boxes,exp[i])
        if(i % 6 == 5){
            hypoProb = calcProbeForHypo(distributionBox, hypoProb)
            fillprobForGraph(hypoProb, dataForGraph)
            for(j in hypoProb.indices){
                sortedHypoProb[j] = hypoProb[j]
            }
            k++
        }
    }
    println("task 1")
    var count = 0
    permutaions().forEach{
        list -> print("$count [")
        list.forEach{ print("${it + 1} ")}
        println("]")
        count++
        }

    count = 0
    sortedHypoProb = sortedHypoProb.toList().sortedBy { (_, v) -> v }.toMap().toMutableMap()
    sortedHypoProb.forEach{(k,v) -> println("$k => $v")}
    println("max ${hypoProb.max()}")
    graph(dataForGraph, k)
}

fun graph(data: MutableList<MutableList<Double>>, k: Int) {
    val xData = doubleArrayOf().toMutableList()
    for(i in 1 .. k){
        xData.add(i*5.0)
    }

    val chart = QuickChart.getChart("Вероятности гипотез", "N", "P","[5 3 1 2 4 6]", xData, data[0])
    chart.addSeries("[5 3 6 2 4 1]",xData, data[1])
    chart.addSeries("[5 3 4 2 1 6]",xData, data[2])
    chart.addSeries("[5 3 1 2 6 4]",xData, data[3])
    chart.addSeries("[5 3 6 2 1 4]",xData, data[4])
    chart.addSeries("[5 3 4 2 6 1]",xData, data[5])
    chart.addSeries("[5 3 1 4 2 6]",xData, data[6])
    chart.addSeries("[5 4 1 2 3 6]",xData, data[7])
    chart.addSeries("[5 3 6 4 2 1]",xData, data[8])
    chart.addSeries("[5 3 4 1 2 6]",xData, data[9])

     SwingWrapper(chart).displayChart()

    BitmapEncoder.saveBitmapWithDPI(chart, "./punk1_1a", BitmapFormat.JPG, 400)

    var chart1 = QuickChart.getChart("Вероятности наилучшей гипотез", "N", "P", "[1 2 3 4 5] ",xData,data[0])

    BitmapEncoder.saveBitmapWithDPI(chart1,"./punkts", BitmapFormat.JPG, 400)
     var chart2 = QuickChart.getChart("Количество первалиующих гипотез","N", "P", "Количество первалиующих гипотез", xData, data[10])
    BitmapEncoder.saveBitmapWithDPI(chart2,"./tastk1c",BitmapFormat.JPG, 400)
    SwingWrapper(chart2).displayChart()


}

fun permutaions(): List<List<Int>> {
    val answer = mutableListOf<List<Int>>()
    for( a1 in 0 ..5)
        for( a2 in 0 ..5)
            for( a3 in 0 ..5)
                for( a4 in 0 ..5)
                    for( a5 in 0 ..5)
                        for( a6 in 0 ..5)
                            if(a1!= a2 && a1!=a3 && a1 != a4 && a1 != a5 && a1 != a6 &&
                               a2 != a3 && a2!= a4 && a2!= a5 && a2 != a6 &&
                                a3!= a4 && a3!=a5 && a3 != a6 && a4!=a5 && a4 != a6 && a5!= a6) answer.add(listOf(a1,a2,a3,a4,a5,a6))
return answer
}
fun fillprobForGraph(probs: ArrayList<Double>, data: MutableList<MutableList<Double>>) {
    data[0].add(probs[528])
    data[1].add(probs[549])
    data[2].add(probs[542])
    data[3].add(probs[529])
    data[4].add(probs[548])
    data[5].add(probs[543])
    data[6].add(probs[530])
    data[7].add(probs[552])
    data[8].add(probs[551])
    data[9].add(probs[540])
    data[10].add(probs.filter{it > 0.1}.size.toDouble())


}

fun calcProbeForHypo(data: ArrayList<List<Double>>, priorprob: ArrayList<Double>): ArrayList<Double> {
    val terms = arrayListOf<Double>()
    for( i in 0 .. 719) terms.add(priorprob[i])
    val perm = permutaions()
    for ( term in 0 .. 719){
        var probOfHypoN = 1.0
        probOfHypoN *= data[0][perm[term][0]]
        probOfHypoN *= data[1][perm[term][1]]
        probOfHypoN *= data[2][perm[term][2]]
        probOfHypoN *= data[3][perm[term][3]]
        probOfHypoN *= data[4][perm[term][4]]
        probOfHypoN *= data[5][perm[term][5]]

        terms [term] *= probOfHypoN
    }
    val prob = terms.sum()
    println("haha $prob")

    val postProb = arrayListOf<Double>()
    for(i in 0 .. 719){
        postProb.add((terms[i])/prob)
    }
    return postProb
}

fun calcProb(boxes: List<Box>, data: List<String>): List<Double> {
    val terms = arrayListOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0)
     for (term in 0..5)
     {
         var proOfBoxN = 1.0
         for(color in data)
            when(color) {
                "Red" -> {
                    proOfBoxN *= boxes[term].red/boxes[term].total()
                    boxes[term].red--
                }
                "White" -> {
                    proOfBoxN *= boxes[term].white/boxes[term].total()
                    boxes[term].white--
                }
                "Black" -> {
                    proOfBoxN *= boxes[term].black/boxes[term].total()
                    boxes[term].black--
                }
                "Green" -> {
                    proOfBoxN *= boxes[term].green/boxes[term].total()
                    boxes[term].green--
                }
                "Blue" -> {
                    proOfBoxN *= boxes[term].blue/boxes[term].total()
                    boxes[term].blue--
                }

            }
         terms[term] *= proOfBoxN
         for(color in data )
             when(color){
                 "Red" -> boxes[term].red++
                 "White" -> boxes[term].white++
                 "Black" -> boxes[term].black++
                 "Green" -> boxes[term].green++
                 "Blue" -> boxes[term].blue++

             }

     }
    return  terms
}
fun task2(iterations: Int){
    val exp = parseData()
    val box1 = Box(20,62,43,59,86)
    val box2 = Box(15,13,83,80,89)
    val box3 = Box(39,32,70,48,21)
    val box4 = Box(19,47,72,40,52)
    val box5 = Box(93,12,69,41,75)
    val box6 = Box(19,81,41,30,69)
    val boxes = listOf(box1,box2,box3,box4,box5,box6)

    val firstBox  = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val secondBox = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val thirdBox  = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val fourthBox = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val fifthBox  = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val sixthBox  = listOf(1.0/6,1.0/6,1.0/6,1.0/6,1.0/6)
    val distributionBox = arrayListOf(firstBox,secondBox,thirdBox,fourthBox,fifthBox,sixthBox)
    val dataForGraph = mutableListOf<MutableList<MutableList<Double>>>()
    for(i in 0..5){
        dataForGraph.add(mutableListOf())
        for(j in 0..4) dataForGraph[i].add(mutableListOf())
    }
    for(i in 0 until iterations){
        distributionBox[i%6] = calcProbTask2(boxes,exp[i], distributionBox[i%6])
        for(j in 0..4){
            dataForGraph[i%6][j].add(distributionBox[i%6][j])
        }
    }
    graphTask2(dataForGraph, iterations)

}

fun graphTask2(data: MutableList<MutableList<MutableList<Double>>>, iterations: Int) {
    val xData = doubleArrayOf().toMutableList()
    for (i in 1..(iterations.toDouble() / 6).toInt()){
        xData.add(i * 6.0)
        }
    println(xData.size)
    println(data[0][0].size)
    val chart1 = QuickChart.getChart("Верояность первой корзиных", "N","P","Первая",xData,data[0][0])
    chart1.addSeries("Вторая", xData, data[0][1])
    chart1.addSeries("Третья", xData, data[0][2])
    chart1.addSeries("Четвертая", xData, data[0][3])
    chart1.addSeries("Пятая", xData, data[0][4])
    SwingWrapper(chart1).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart1,"./task2_urn1",BitmapFormat.JPG,400)

    val chart2 = QuickChart.getChart("Верояность Вторая корзиных", "N","P","Первая",xData,data[1][0])
    chart2.addSeries("Вторая", xData, data[1][1])
    chart2.addSeries("Третья", xData, data[1][2])
    chart2.addSeries("Четвертая", xData, data[1][3])
    chart2.addSeries("Пятая", xData, data[1][4])
    SwingWrapper(chart2).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart2,"./task2_urn2",BitmapFormat.JPG,400)

    val chart3 = QuickChart.getChart("Верояность Третей корзиных", "N","P","Первая",xData,data[2][0])
    chart3.addSeries("Вторая", xData, data[2][1])
    chart3.addSeries("Третья", xData, data[2][2])
    chart3.addSeries("Четвертая", xData, data[2][3])
    chart3.addSeries("Пятая", xData, data[2][4])
    SwingWrapper(chart3).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart3,"./task2_urn3",BitmapFormat.JPG,400)

    val chart4 = QuickChart.getChart("Верояность Четвертой корзиных", "N","P","Первая",xData,data[3][0])
    chart4.addSeries("Вторая", xData, data[3][1])
    chart4.addSeries("Третья", xData, data[3][2])
    chart4.addSeries("Четвертая", xData, data[3][3])
    chart4.addSeries("Пятая", xData, data[3][4])
    SwingWrapper(chart4).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart4,"./task2_urn4",BitmapFormat.JPG,400)

    val chart5 = QuickChart.getChart("Верояность Пятой корзиных", "N","P","Первая",xData,data[4][0])
    chart5.addSeries("Вторая", xData, data[4][1])
    chart5.addSeries("Третья", xData, data[4][2])
    chart5.addSeries("Четвертая", xData, data[4][3])
    chart5.addSeries("Пятая", xData, data[4][4])
    SwingWrapper(chart5).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart5,"./task2_urn5",BitmapFormat.JPG,400)

    val chart6 = QuickChart.getChart("Верояность 6-ой корзиных", "N","P","Первая",xData,data[5][0])
    chart6.addSeries("Вторая", xData, data[5][1])
    chart6.addSeries("Третья", xData, data[5][2])
    chart6.addSeries("Четвертая", xData, data[5][3])
    chart6.addSeries("Пятая", xData, data[5][4])
    SwingWrapper(chart6).displayChart()
    BitmapEncoder.saveBitmapWithDPI(chart6,"./task2_urn6",BitmapFormat.JPG,400)


}

fun calcProbTask2(boxes: List<Box>, data: List<String>, priorProb: List<Double>): List<Double> {
val terms = arrayListOf<Double>()
    for(i in 0 ..4){
        terms.add(priorProb[i])
    }
    for(term in 0 .. 4){
        var proOfBoxN = 1.0
        for (color in data)
            when(color){
                "Red" -> {
                    proOfBoxN *= boxes[term].red/boxes[term].total()
                    boxes[term].red--
                }
                "White" -> {
                    proOfBoxN *= boxes[term].white/boxes[term].total()
                    boxes[term].white--
                }
                "Black" -> {
                    proOfBoxN *= boxes[term].black/boxes[term].total()
                    boxes[term].black--
                }
                "Green" -> {
                    proOfBoxN *= boxes[term].green/boxes[term].total()
                    boxes[term].green--
                }
                "Blue" -> {
                    proOfBoxN *= boxes[term].blue/boxes[term].total()
                    boxes[term].blue--
                }
            }
        terms[term] *= proOfBoxN
        for(color in data )
            when(color){
                "Red" -> boxes[term].red++
                "White" -> boxes[term].white++
                "Black" -> boxes[term].black++
                "Green" -> boxes[term].green++
                "Blue" -> boxes[term].blue++

            }
    }
    var prob = 0.0
    for( i in 0..4) prob += terms[i]
    val postProb = arrayListOf(0.0,0.0,0.0,0.0,0.0)
    for( i in 0..4) {
        postProb[i] = terms[i]/prob
    }
    return postProb
}
fun task3(iterations: Int){
    val exp = parseData()
    val box1 = Box(20,62,43,59,86)
    val box2 = Box(15,13,83,80,89)
    val box3 = Box(39,32,70,48,21)
    val box4 = Box(19,47,72,40,52)
    val box5 = Box(93,12,69,41,75)
    val box6 = Box(19,81,41,30,69)
    val boxes = listOf(box1,box2,box3,box4,box5,box6)

    val firstBox  = listOf(1.0,1.0,1.0,1.0,1.0)
    val secondBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val thirdBox  = listOf(1.0,1.0,1.0,1.0,1.0)
    val fourthBox = listOf(1.0,1.0,1.0,1.0,1.0)
    val fifthBox  = listOf(1.0,1.0,1.0,1.0,1.0)
    val sixthBox  = listOf(1.0,1.0,1.0,1.0,1.0)
    val distributionBox = arrayListOf(firstBox,secondBox,thirdBox,fourthBox,fifthBox,sixthBox)
    var probDistBox: List<List<Double>> = emptyList()
    val dataForGraph = mutableListOf<MutableList<MutableList<Double>>>()

    for(i in 0..5){
        dataForGraph.add(mutableListOf())
        for(j in 0..4) dataForGraph[i].add(mutableListOf())
    }
    for(i in 0 until iterations){
        distributionBox[i%6] = calcCnOfBalls(exp[i], distributionBox[i%6])
        probDistBox= calacDistrProb(distributionBox)
        for(j in 0..4){
            dataForGraph[i%6][j].add(probDistBox[i%6][j])
        }
    }
    val theory = calacDistrProbBox(boxes)
    println("________3 Task________________")
    println("Рсапределение цветных шариков по коробкам из $iterations итераций")
    println("1 коробка:       " + probDistBox[0])
    println("2 коробка:       " + probDistBox[1])
    println("3 коробка:       " + probDistBox[2])
    println("4 коробка:       " + probDistBox[3])
    println("5 коробка:       " + probDistBox[4])
    println("6 коробка:       " + probDistBox[5])

    println("теоретическая верояность шариков")
    println("1 коробка:       " + theory[0])
    println("2 коробка:       " +  theory[1])
    println("3 коробка:       " + theory[2])
    println("4 коробка:       " +  theory[3])
    println("5 коробка:       " +  theory[4])
    println("6 коробка:       " + theory[5])

    graph3(dataForGraph, iterations)

    val perms = permutaions()
    var minDelta = 5.0
    var answ = mutableListOf<Int>()
    for(perm in perms){
        var delta = 0.0
        for(i in 0..4){
            for (j in 0..4){
                delta += abs(theory[perm[i]][j]- probDistBox[i][j])
            }
        }
        if(minDelta > delta){
            minDelta = delta
            answ = perm.toMutableList()
        }
    }
    for(i in 0..5){
        answ[i]++
    }
    println("Оптимальная перестановка с минимальными отклюнением от теоретического -$answ")
    println("Отклонение $minDelta")
}

fun calcCnOfBalls(data: List<String>, cntOfBalls: List<Double>): List<Double> {
    val terms = arrayListOf<Double>()
    for(i in 0..4){
        terms.add(cntOfBalls[i])
    }
    for(color in data){
       when(color){
           "Red" -> terms[0]++
           "White" -> terms[1]++
           "Black" -> terms[2]++
           "Green" -> terms[3]++
           "Blue" -> terms[4]++

       }


    }
    return terms
}

fun calacDistrProb(data: ArrayList<List<Double>>): List<List<Double>> {
    val answ = listOf<MutableList<Double>>(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(),
        mutableListOf(), mutableListOf())
    for(i in 0..5)
        for(j in 0..4){
            answ[i].add(data[i][j]/data[i].sum())
        }
    return answ
}

fun graph3(data: MutableList<MutableList<MutableList<Double>>>, iterations: Int) {
     val xData = doubleArrayOf().toMutableList()
    for(i in 1 ..(iterations.toDouble() / 6).toInt()){
        xData.add(i.toDouble()*6)
    }
    print(xData.size)
    val chart1 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[0][0])
    chart1.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart1.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart1.addSeries("White",xData,data[0][1])
    chart1.addSeries("Black",xData,data[0][2])
    chart1.addSeries("Green",xData,data[0][3])
    chart1.addSeries("Blue",xData,data[0][4])

    val chart2 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[1][0])
    chart2.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart2.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart2.addSeries("White",xData,data[1][1])
    chart2.addSeries("Black",xData,data[1][2])
    chart2.addSeries("Green",xData,data[1][3])
    chart2.addSeries("Blue",xData,data[1][4])
    val chart3 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[2][0])
    chart3.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart3.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart3.addSeries("White",xData,data[2][1])
    chart3.addSeries("Black",xData,data[2][2])
    chart3.addSeries("Green",xData,data[2][3])
    chart3.addSeries("Blue",xData,data[2][4])
    val chart4 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[3][0])
    chart4.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart4.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart4.addSeries("White",xData,data[3][1])
    chart4.addSeries("Black",xData,data[3][2])
    chart4.addSeries("Green",xData,data[3][3])
    chart4.addSeries("Blue",xData,data[3][4])
    val chart5 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[4][0])
    chart5.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart5.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart5.addSeries("White",xData,data[4][1])
    chart5.addSeries("Black",xData,data[4][2])
    chart5.addSeries("Green",xData,data[4][3])
    chart5.addSeries("Blue",xData,data[4][4])
    val chart6 = QuickChart.getChart("Профиль первой корзниныб", "N","P","Red",xData, data[5][0])
    chart6.styler.seriesColors = arrayOf(Color.red, Color.gray, Color.BLACK, Color(27,190,20), Color(0,85,205))
    chart6.styler.seriesMarkers = arrayOf(SeriesMarkers.NONE,SeriesMarkers.NONE, SeriesMarkers.NONE,SeriesMarkers.NONE,SeriesMarkers.NONE)
    chart6.addSeries("White",xData,data[5][1])
    chart6.addSeries("Black",xData,data[5][2])
    chart6.addSeries("Green",xData,data[5][3])
    chart6.addSeries("Blue",xData,data[5][4])
    BitmapEncoder.saveBitmapWithDPI(chart1,"./tassk3c_1",BitmapFormat.JPG,400)
    BitmapEncoder.saveBitmapWithDPI(chart2,"./tassk3c_2",BitmapFormat.JPG,400)
    BitmapEncoder.saveBitmapWithDPI(chart3,"./tassk3c_3",BitmapFormat.JPG,400)
    BitmapEncoder.saveBitmapWithDPI(chart4,"./tassk3c_4",BitmapFormat.JPG,400)
    BitmapEncoder.saveBitmapWithDPI(chart5,"./tassk3c_5",BitmapFormat.JPG,400)
    BitmapEncoder.saveBitmapWithDPI(chart6,"./tassk3c_6",BitmapFormat.JPG,400)


}

fun calacDistrProbBox(boxes: List<Box>): List<List<Double>> {
     val answ = listOf<MutableList<Double>>(mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(),
         mutableListOf(), mutableListOf())
    for( i in 0..5){
        answ[i].add(boxes[i].red/boxes[i].total())
        answ[i].add(boxes[i].white/boxes[i].total())
        answ[i].add(boxes[i].black/boxes[i].total())
        answ[i].add(boxes[i].green/boxes[i].total())
        answ[i].add(boxes[i].blue/boxes[i].total())

    }
    return answ
}
