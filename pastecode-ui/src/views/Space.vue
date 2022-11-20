<template>
  <el-row>
    <div id="space">
      <pc-header></pc-header>
      <user-info></user-info>
      <user-code></user-code>
    </div>
  </el-row>
</template>

<script>
import UserInfo from "@/components/UserInfo";
import UserCode from "@/components/UserCode";
import PcHeader from "@/components/pc-header";
export default {
  name: "Space.vue",
  components: {PcHeader, UserCode, UserInfo},
  data() {
    return {
      currentDate: new Date()
    }
  },
  methods: {

  },
  mounted() {

  }

}
</script>

<style scoped>
#space {
  text-align: center;
}
</style>