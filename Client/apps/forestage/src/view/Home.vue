<script>
import HeadTop from '../components/HeadTop.vue'
import CarouselMap from '../components/CarouselMap.vue'
import SearchBox from '../components/SearchBox.vue';
export default{
  name:"Home",
  components:{
    HeadTop,
    CarouselMap,
    SearchBox,
  }
}
</script>

<template>
    <div>
      <HeadTop></HeadTop>
      <SearchBox></SearchBox>
      <CarouselMap></CarouselMap>
    </div>
</template>
