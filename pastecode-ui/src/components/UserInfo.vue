<template>
  <div id="user-info" v-if="show" v-loading.fullscreen.lock="loading">
    <el-col :offset="2" :span="4">
      <el-card :body-style="{ padding: '0px' }" class="mt-10">
        <el-image :preview-src-list="srcList" :src="srcList[0]"  class="mt-10" fit="cover" style="width: 90%"/>
        <div style="padding: 14px;">
          <span><b>{{ user.userName }}</b></span>
        </div>
        <el-divider style="width: 80%"></el-divider>
        <div style="padding: 14px">
          <span class="info">分享代码数：{{ user.userCodeNum }}</span>
          <span class="info">{{ user.userEmail }}</span>
          <span class="info">
            <el-link v-if="user.userAccount === userAccountStorage" :underline="false" @click="edit">修改个人资料</el-link>
          </span>
        </div>
      </el-card>
    </el-col>

    <el-backtop :visibility-height="0"></el-backtop>

    <el-dialog :visible.sync="editUserVisible" center destroy-on-close title="修改个人资料">
      <update-user-form
          :user-prop="Object.assign({}, this.user)"
          :key="new Date().getTime()"
          @exit="exit"
          @reload="reload"></update-user-form>
    </el-dialog>

  </div>
</template>

<script>

import UpdateUserForm from '@/components/admin/user-management/update-user-form'

export default {
  name: "UserInfo",
  components: {UpdateUserForm},
  data() {
    return {
      loading: true,
      show: false,
      srcList: [],
      editUserVisible: false,
      userAccountStorage: localStorage.getItem("userAccount"),
      user: {}
    }
  },
  methods: {

    exit() {
      this.editUserVisible = false;
    },

    async reload() {
      await this.load();
      this.editUserVisible = false;
      this.$router.go(0);
    },

    async load() {

      console.log("调用load", this.user.userAccount);

      await this.$axios({
        method: "get",
        url: `${this.$url}/user/info/${this.user.userAccount}`
      }).then((response) => {

        let resp = response.data.data;

        console.log("user", JSON.stringify(this.user));

        console.log(response);

        this.user = resp;

        localStorage.setItem("userName", this.user.userName);

        this.srcList[0] = require("@/assets/" + resp.userAvatar);

        setTimeout(() => {
          this.loading = false;
        }, 150);

        // this.user.userAvatar = this.srcList[0];

      })
    },

    edit() {
      this.editUserVisible = true;
    }
  },
  async mounted() {
    this.user.userAccount = this.$route.params.account;
    await this.load();
    this.show = true;
  }
}
</script>

<style scoped>
.mt-10 {
  margin-top: 10px;
}

.el-divider--horizontal {
  width: 80% !important;
  margin-left: 10% !important;
  margin-right: 10% !important;
}

.el-divider {
  margin: 0;
}

b {
  font-size: 18px;
}

.info {
  display: block;
  margin-bottom: 10px;
}
</style>