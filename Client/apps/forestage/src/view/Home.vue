<!-- eslint-disable vue/multi-word-component-names -->
<script>
import CarouselMap from '../components/CarouselMap.vue'
import SearchBox from '../components/SearchBox.vue';
import OrderList from '../components/OrderList.vue';

export default{
  name:"Home",
  components:{
    CarouselMap,
    SearchBox,
    OrderList,
  }
}
</script>

<template>
    <div>
      
      <OrderList></OrderList>
      <CarouselMap></CarouselMap>
    </div>
</template>
