<template>
  <div id="log-management">
    <el-form inline ref="codeForm" :model="log">
      <el-form-item label="操作类型" prop="logType">
        <el-select placeholder="请选择操作类型" v-model="log.logType" clearable>
          <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间" prop="">
        <el-date-picker
            v-model="time"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
        </el-date-picker>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="load">搜索</el-button>
      <el-button icon="el-icon-refresh" @click="refresh">重置</el-button>
    </el-form>
    <div id="table">
      <el-table
          :data="logList"
          @selection-change="handleSelectionChange">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-descriptions :column="5"
                             border
                             direction="vertical">
              <el-descriptions-item
                  v-for="(item, key, index) in scope.row.logInfo"
                  :key="index"
                  :label="key">
                {{ item }}
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column
            prop="logId"
            label="编号">
        </el-table-column>
        <el-table-column label="操作反馈">
          <template slot-scope="scope">
            <span v-if="scope.row.logIsSuccess" style="color: #67c23a"><i class="el-icon-check" />执行成功</span>
            <span v-if="!scope.row.logIsSuccess" style="color: #f56c6c"><i class="el-icon-close" />执行失败</span>
          </template>
        </el-table-column>
        <el-table-column label="操作类型">
          <template slot-scope="scope">
            <el-tag type="warning"
                v-if="scope.row.logType === '登录' ||
                      scope.row.logType === '注册'">
              {{ scope.row.logType }}
            </el-tag>
            <el-tag
                v-if="scope.row.logType.indexOf('赞') !== -1 ||
                      scope.row.logType.indexOf('收藏') !== -1 ||
                      scope.row.logType.indexOf('分享') !== -1">
              {{ scope.row.logType }}
            </el-tag>
            <el-tag type="danger"
                v-if="scope.row.logType.indexOf('运行') !== -1 ||
                      scope.row.logType.indexOf('删除') !== -1 ||
                      scope.row.logType.indexOf('更新') !== -1">
              {{ scope.row.logType }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="logCreateDate"
            label="操作时间">
        </el-table-column>
      </el-table>
      <el-pagination
          background
          layout="total, prev, pager, next"
          :total=total
          :page-size="size"
          :current-page="current"
          @current-change="currentChange"
      >
      </el-pagination>
    </div>

  </div>
</template>

<script>
import Prism from "prismjs";


import PcCode from "@/components/pc-code";
import UpdateCodeForm from "@/components/admin/codeManagement/UpdateCodeForm";
import moment from "moment";

export default {
  name: "LogManager",
  components: {PcCode, UpdateCodeForm},
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      types: [],
      tableSelects: [],
      deleteDisabled: true,
      updateDisabled: true,
      logList: [],
      time: '',
      total: 0,
      current: 1,
      size: 5,
      jsonStr: '',
      allDelete: false,
      updateCodeVisible: false,
      insertCodeVisible: false,
      codeProp: {},
      log: {
        logId: 0,
        logInfo: '',
        logIsSuccess: false,
      },
      userProp: {},
      userTemp: {}
    }
  },
  methods: {

    headUpdate() {
      if (this.tableSelects.length === 1) {
        this.codeProp = this.tableSelects[0];
        this.updateCodeVisible = true;
      }
    },
    async headDelete() {
      if (this.tableSelects.length > 0) {
        this.allDelete = true;
        // 使用 for of 是因为 forEach 内部处理异步操作时
        // 则 forEach 同时也处于异步状态，并不会阻塞进程，而是让下面的语句先执行
        for (const value of this.tableSelects) {
          await this.delete(value.codeId);
          console.log(this.allDelete);
        }
        this.allDelete = false;
        this.$message({
          message: '删除成功',
          duration: 1000,
          type: 'success'
        });
        await this.load();
      }
    },

    handleSelectionChange(val) {
      this.tableSelects = val;
      val.length === 1 ? this.updateDisabled = false : this.updateDisabled = true;
      val.length  >  0 ? this.deleteDisabled = false : this.deleteDisabled = true;
    },
    insert() {

    },
    exit() {
      this.updateCodeVisible = false;
    },
    async reload() {
      await this.load();
      this.updateCodeVisible = false;
    },
    handleEdit(index, row) {
      // this.userProp = row;
      this.codeProp = row;
      this.updateCodeVisible = true;
      // this.userTemp = Object.assign({}, row);
      // this.updateCodeVisible = true;
      console.log(JSON.stringify(row));
    },
    handleDelete(index, row) {
      // console.log(JSON.stringify(row));
      this.delete(row.codeId);
    },

    async init() {
      await this.initTypes();
      await this.load();
    },

    async initTypes() {
      await this.$axios({
        method: "get",
        url: `${this.$url}/log/getLogType`
      }).then((response) => {
        console.log(response.data.data);

        let typeList = response.data.data;

        this.types = [];

        typeList.forEach((value) => {
          this.types.push({
            "value": value,
            "label": value
          });
        });

      });
    },

    async load(key) {

      let startDate = null;
      let endDate = null;

      if (this.time !== null) {
        startDate = moment(this.time[0]).format('YYYY-MM-DD');
        endDate = moment(this.time[1]).format('YYYY-MM-DD');
      }

      if (startDate === endDate) {
        startDate = null;
        endDate = null;
      }

      if (key != null && key.type === "click") {
        this.current = 1;
      }
      await this.$axios({
        method: "get",
        url: `${this.$url}/log/pageList`,
        params: {
          "logType": this.log.logType,
          "startDate": startDate,
          "endDate": endDate,
          "current": this.current - 1,
        }
      }).then((response) => {
        if (response.data.flag === true) {

          let resp = response.data.data;
          this.logList = resp.records;
          this.total = resp.count;

          console.log(resp.records);

          for (let log of this.logList) {
            // \t 代表添加缩进美化
            // log.logInfo = JSON.stringify(JSON.parse(log.logInfo), null, '\t');
            log.logInfo = JSON.parse(log.logInfo);

            for (let key in log.logInfo) {
              console.log(key, log.logInfo[key]);
            }
          }
        }
      })
    },

    next() {
      this.current ++;
      this.load();
    },

    previous() {
      this.current --;
      this.load();
    },

    currentChange(current) {
      this.current = current;
      this.load();
    },

    refresh() {
      this.$refs['codeForm'].resetFields();
      this.time = null;
      this.load();
    },

    async delete(codeId) {
      await this.$axios({
        method: "post",
        url: `${this.$url}/code/delete`,
        data: {
          "codeId": codeId,
        }
      }).then((response) => {
        if (response.data.flag === true && this.allDelete === false) {

          this.$message({
            message: '删除成功',
            duration: 1000,
            type: 'success'
          });

          this.load();
        }
      })
    }
  },
  mounted() {
    this.init();
  }
}
</script>

<style scoped>
.el-pagination{
  margin-top: 10px;
  float: right;
}
.el-table{
  margin-top: 10px;
}
.el-tag {
  font-size: 14px;
}
>>> .el-descriptions-item__cell {
  text-align: center !important;
  font-size: 16px;
}
</style>