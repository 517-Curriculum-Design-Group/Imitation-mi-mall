<template >
    商品管理
    <div>
        {{ list }}
    </div>
    
</template>

<script setup>
import {getproductlist} from '~/api/product'
import {ref} from 'vue'

const list = ref([])
getproductlist(1,10)
.then((res)=>{
    console.log(res)
    list.value = res
    console.log(list)
})

</script>
