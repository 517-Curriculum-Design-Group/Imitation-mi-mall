<script>
import HeadTop from '../components/HeadTop.vue'
import CarouselMap from '../components/CarouselMap.vue'
import SearchBox from '../components/SearchBox.vue';
import OrderList from '../components/OrderList.vue';

export default{
  name:"Home",
  components:{
    HeadTop,
    CarouselMap,
    SearchBox,
    OrderList,
  }
}
</script>

<template>
    <div>
      <HeadTop></HeadTop>
      <!-- <SearchBox></SearchBox> -->
      <OrderList></OrderList>
      <CarouselMap></CarouselMap>
      
    </div>
</template>
