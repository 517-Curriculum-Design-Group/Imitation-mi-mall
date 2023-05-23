<script setup>
import { ref, reactive, onMounted } from 'vue'
import { api } from '@/api'

const isShow = ref(false)

const userInfo = reactive({
    name: '',
    mobile: '',
    address: '',
})

const value = ref(null)
const options = [
    {
        value: "广西",
        label: "广西",
        children: [{
            value: "桂林市",
            label: "桂林市"
        },
        {
            value: "南宁市",
            label: "南宁市",
        }
        ]
    }
]

let allAddresses = ref(null)
onMounted(async () => {
    const [e, r] = await api.getAllAddresses()
    
})
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">收货地址</h1>
    
    <div class="flex flex-wrap">
        
        <div class="container flex flex-col justify-center items-center w-[270px] h-[180px] border-1 border-solid border-gray-400 cursor-pointer hover:border-gray-500"
            @click="isShow = true">
            <span class="icon i-mdi-plus-circle text-gray-200 text-5xl mb-2 hover:text-gray-400"></span>
            <h4 class="text-gray-400">添加新地址</h4>
        </div>


    </div>

    <n-modal v-model:show="isShow">
        <n-form :model="userInfo" class="flex userForm flex-col w-[660px] h-[280px] shadow-lg bg-light-50 p-4 t-[100px]"
            :class="{ 'userForm-active': isShow }">
            <div class="flex justify-between w-[635px] h-[60px] pt-[14px] pb-[14px] pl-[20px] pr-[20px] bg-gray-100">
                <h2>添加收货地址</h2>
            </div>
            <n-space>
                <n-form-item :path="name" :rule="{
                    required: true,
                    message: '请输入姓名',
                    trigger: ['input', 'blur']
                }">
                    <n-input v-model:value="userInfo.name" placeholder="姓名" clearable style="width:303px;height:40px" />
                </n-form-item>

                <n-form-item :path="name" placeholder="手机号" :rule="{
                    required: true,
                    message: '请输入号码',
                    trigger: ['input', 'blur']
                }">
                    <n-input v-model:value="userInfo.mobile" clearable placeholder="手机号" style="width:303px;height:40px" />
                </n-form-item>
            </n-space>

            <n-cascader tabindex="1" v-model:value="value" placeholder="请选择地址" expand-trigger='hover' :options="options"
                check-strategy='child' class="address"
                style="--n-border-hover:1px solid var(--button-background-color) !important;--n-border-focus:1px solid var(--button-background-color);width:620px;height:40px;" />

            <div class="flex justify-center gap-8 w-[660px] h-[81px] mt-4">
                <n-button class="text-light-50 bg-orange-500 w-[160px] h-[40px]" attr-type="button"
                    style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white">
                    确定
                </n-button>
                <n-button class="text-light-50 bg-gray-400 w-[160px] h-[40px]" attr-type="button"
                    style="--n-color-hover:gray;--n-border-hover:gray;--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white"
                    @click="isShow = false">
                    取消
                </n-button>
            </div>
        </n-form>
    </n-modal>
</template>

   <!-- 新增 -->
<!-- //   <n-modal
//     v-model:show="userAdress.flag"
//     :trap-focus="false"
//     :mask-closable="false"
//   >
//     <n-form
//       :model="userAdress.address"
//       class="flex userForm flex-col w-[660px] h-auto shadow-lg bg-light-50 p-4 t-[100px]"
//     >
//       <div
//         class="flex justify-between w-[635px] h-[60px] pt-[14px] pb-[14px] pl-[20px] pr-[20px] bg-gray-100"
//       >
//         <h2>新增收货地址</h2>
//       </div>
//       <n-space tabindex="1">
//         <n-form-item label="收货人"
//         :rule="{
//                     required: true,
//                     message: '请输入姓名',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.recipientName"
//             type="text"
//             placeholder="姓名"
//             clearable
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item label="收货人手机号" type="text" :rule="{
//                     required: true,
//                     message: '请输入手机号',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.recipientPhone"
//             clearable
//             placeholder="手机号"
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item label="省"
//         :rule="{
//                     required: true,
//                     message: '请输入省份',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.province"
//             placeholder="省份"
//             type="text"
//             clearable
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item label="市"
//         :rule="{
//                     required: true,
//                     message: '请输入城市',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.city"
//             placeholder="城市"
//             type="text"
//             clearable
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item label="区"
//         :rule="{
//                     required: true,
//                     message: '请输入区/县',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.district"
//             placeholder="区/县"
//             type="text"
//             clearable
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item label="镇"
//         :rule="{
//                     required: true,
//                     message: '请输入乡/镇',
//                     trigger: ['blur']
//                 }">
//           <n-input
//             v-model:value="userAdress.address.zhen"
//             type="text"
//             placeholder="镇/乡"
//             clearable
//             style="width: 303px; height: 40px"
//           />
//         </n-form-item>

//         <n-form-item path="address" label="收货地址" placeholder="手机号"
//         :rule="{
//                     required: true,
//                     message: '请输入详细地址',
//                     trigger: ['blur']
//                 }"
//         >
//           <n-input
//             v-model:value="userAdress.address.detail"
//             clearable
//             placeholder="收货地址"
//             style="width: 606px; height: 40px"
//           />
//         </n-form-item>
//       </n-space>

//       <div class="flex justify-center gap-8 w-[660px] h-[81px] mt-4">
//         <n-button
//           class="text-light-50 bg-orange-500 w-[160px] h-[40px]"
//           attr-type="button"
//           style="
//             --n-color-hover: var(--button-background-color);
//             --n-border-hover: var(--n-border-hover);
//             --n-text-color-hover: white;
//             --n-boreder-focus: var(--button-background-color);
//             --n-ripple-color: var(--button-background-color);
//             --n-color-focus: gray;
//             --n-text-color-focus: white;
//           "
//           @click="add"
//         >
//           确定
//         </n-button>
//         <n-button
//           class="text-light-50 bg-gray-400 w-[160px] h-[40px]"
//           attr-type="button"
//           style="
//             --n-color-hover: gray;
//             --n-border-hover: gray;
//             --n-text-color-hover: white;
//             --n-boreder-focus: var(--button-background-color);
//             --n-ripple-color: var(--button-background-color);
//             --n-color-focus: gray;
//             --n-text-color-focus: white;
//           "
//           @click="cancel()"
//         >
//           取消
//         </n-button>
//       </div>
//     </n-form>
//   </n-modal> -->


<style scoped lang="scss">
// .userForm{
//     visibility: hidden;
//     transition: all 0.2s ease-in-out;
// }

// .userForm-active{
//     visibility: visible;
//     transition: all 0.2s ease-in-out;
// }
</style>