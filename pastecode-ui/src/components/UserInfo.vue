<template>
  <div id="user-info">
    <el-col :offset="2" :span="4">
      <el-card :body-style="{ padding: '0px' }" class="mt-10">
        <el-image :preview-src-list="srcList" :src="srcList[0]" class="mt-10" fit="cover" style="width: 90%"/>
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


    <el-dialog :visible.sync="editUserVisible" center destroy-on-close title="修改个人资料">
      <!--      <update-user-form-->
      <!--          :user-prop="Object.assign({}, this.userProp)"-->
      <!--          :key="new Date().getTime()"-->
      <!--          @exit="exit"-->
      <!--          @reload="reload"></update-user-form>-->
      <update-user-form
          :user-prop="Object.assign({}, this.user)"
          :key="new Date().getTime()"></update-user-form>
    </el-dialog>

  </div>
</template>

<script>
import UpdateUserForm from "@/components/UpdateUserForm";

export default {
  name: "UserInfo",
  components: {UpdateUserForm},
  data() {
    return {
      srcList: [],
      editUserVisible: false,
      userAccountStorage: localStorage.getItem("userAccount"),
      user: {}
    }
  },
  methods: {
    load() {
      this.$axios({
        method: "get",
        url: `${this.$url}/user/info/${this.user.account}`
      }).then((response) => {

        let resp = response.data.data;

        this.user = resp;


        this.srcList[0] = require("@/assets/" + resp.userAvatar);
        // this.user.userAvatar = this.srcList[0];

      })
    },

    edit() {
      this.editUserVisible = true;
    }
  },
  mounted() {
    this.user.account = this.$route.params.account;
    this.load();
    console.log(this.userAccountStorage, this.user.account)
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