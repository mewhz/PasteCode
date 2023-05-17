<template>
  <div id="pc-like" style="display: inline">
    <svg class="icon" aria-hidden="true" @click="upLike" style="font-size: 2rem;">
      <use xlink:href="#icon-dianzan" v-if="!isLike"></use>
      <use xlink:href="#icon-dianzanxin" v-if="isLike"></use>
    </svg>
    <span>{{ likesNum }}</span>
    <svg class="icon" aria-hidden="true" @click="upCollect" style="font-size: 2rem;">
      <use xlink:href="#icon-jiaxingshoucang" v-if="!isCollect"></use>
      <use xlink:href="#icon-jiaxingshoucangtianchong" v-if="isCollect"></use>
    </svg>
    <span>{{ collectNum }}</span>
  </div>
</template>

<script>

import moment from 'moment'

export default {
  name: "PcCodeStatus",
  props:{
    code: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      likesNum: 0,
      collectNum: 0,
      userId: localStorage.getItem("userId"),
      isLike: false,
      isCollect: false,
    }
  },
  methods: {

    load() {

      this.$axios({
        method: "get",
        url: `${this.$url}/status/total/1/${this.code.codeId}`
      }).then((response) => {
        this.likesNum = response.data.data;
        console.log("likes", this.likesNum);
      });

      this.$axios({
        method: "get",
        url: `${this.$url}/status/userIsStatus/1/${this.userId}/${this.code.codeId}`,
      }).then((response) => {
        this.isLike = response.data.data;
      });


      this.$axios({
        method: "get",
        url: `${this.$url}/status/total/0/${this.code.codeId}`
      }).then((response) => {
        this.collectNum = response.data.data;
        console.log("collect", this.collectNum)
      });

      this.$axios({
        method: "get",
        url: `${this.$url}/status/userIsStatus/0/${this.userId}/${this.code.codeId}`,
      }).then((response) => {
        console.log("collect", response.data.data);
        this.isCollect = response.data.data;
      });

      console.log(this.collectNum, this.likesNum)
    },

    upCollect() {
      this.isCollect = ! this.isCollect;

      if (this.isCollect) {
        this.collectNum ++;
      }
      else {
        this.collectNum --;
      }
      console.log("userId", this.userId);
      this.$axios({
        method: "post",
        url: `${this.$url}/status/update`,
        data: {
          "userId": this.userId,
          "codeId": this.code.codeId,
          "statusType": 0,
          "statusCreateDate": moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        }
      }).then((response) => {
        console.log(response.data);

        this.load();
      });
    },

    upLike() {

      console.log("userId", this.userId);

      this.isLike = ! this.isLike;
      if (this.isLike) {
        this.likesNum ++;
      }
      else {
        this.likesNum --;
      }

      this.$axios({
        method: "post",
        url: `${this.$url}/status/update`,
        data: {
          "userId": this.userId,
          "codeId": this.code.codeId,
          "statusType": 1,
          "statusCreateDate": moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        }
      }).then((response) => {
        console.log(response.data);
        this.load();
      });

    }
  },
  mounted() {
    this.load();
  }
}
</script>

<style scoped>
span {
  margin-left: 5px;
}
span {
  margin-right: 15px;
  /* rem 相对相对根元素修改大小 */
  font-size: 2rem;
}
</style>