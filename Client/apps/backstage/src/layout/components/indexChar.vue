<template>
    <el-card shadow="never" :body-style="{ padding: '20px' }">
        <template #header>
        <div>
            <span class="text-sm">订单统计</span>
        </div>
        </template>
        <div  id="chart" style="width: 100%;height: 300px;"></div>
    </el-card>
    
</template>

<script setup>
import * as echarts from 'echarts';
import {home} from "~/api/manager.js"
import {ref,onMounted,onBeforeUnmount} from 'vue'
const datas = ref([])


var myChart = null
onMounted(() => {
    var chartDom = document.getElementById('chart');
    if(chartDom){
        myChart = echarts.init(chartDom);
        getData()
    }
})

onBeforeUnmount(()=>{
    if(myChart) echarts.dispose(myChart)
})

function getData() {
    let option = {
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [],
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                }
            }
        ]
    };

home()
.then(res=>{
    // datas.value = res
    console.log(res.YList);
    option.xAxis.data = res.XList
    option.series[0].data = res.YList

    myChart.setOption(option)

    // console.log(res);
})
    

}


</script>
