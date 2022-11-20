<template>
  <div id="user-info" v-if="show">
    <el-col :span="16" :offset="1">
      <el-card :body-style="{ padding: '0px' }" class="mt-10" >
        <el-tabs type="border-card">
          <el-tab-pane label="分享"><user-share-code :user-id="userId"></user-share-code></el-tab-pane>
          <el-tab-pane label="消息" v-if="showMessage"><user-message :user-id="userId"></user-message></el-tab-pane>
<!--          <el-tab-pane label="动态"><user-all-code></user-all-code></el-tab-pane>-->
          <el-tab-pane label="收藏"><user-collect-code :user-id="userId"></user-collect-code></el-tab-pane>
        </el-tabs>
      </el-card>
    </el-col>
  </div>
</template>

<script>
import UserAllCode from "@/components/UserAllCode";
import UserShareCode from "@/components/UserShareCode";
import UserManagement from "@/views/admin/UserManagement";
import UserMessage from "@/components/space/user-code/user-message";
import UserCollectCode from "@/components/space/user-code/user-collect-code";
export default {
  name: "UserCode",
  components: {UserCollectCode, UserMessage, UserShareCode, UserAllCode},
  data() {
    return {
      showMessage: false,
      userId: '',
      userAccount: '',
      show: false,
    }
  },
  methods: {
    async load() {

      this.userAccount = window.location.pathname.replace("/space/", "");

      await this.$axios({
        method: "get",
        url: `${this.$url}/user/getUserId/${this.userAccount}`
      }).then((response) => {
        this.userId = response.data.data;
        this.showMessage = (this.userId.toString()) === localStorage.getItem("userId");
      });
    }
  },
  async created() {
    await this.load();
    this.show = true;
  }
}
</script>

<style scoped>
.mt-10 {
  margin-top: 10px;
}
.el-divider--horizontal{
  width: 80% !important;
  margin-left: 10% !important;
  margin-right: 10% !important;
}
.el-divider {
  margin: 8px;
}
b{
  font-size: 18px;
}
</style>