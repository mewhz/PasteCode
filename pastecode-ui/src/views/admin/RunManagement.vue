<template>
  <div id="run-management">
    <el-form inline ref="runForm" :model="code">
      <el-form-item label="用户账号" prop="userAccount">
        <el-input placeholder="请输入用户账号" v-model="code.userAccount"></el-input>
      </el-form-item>
      <el-form-item label="代码语言" prop="codeType">
        <el-select placeholder="请选择代码语言" v-model="code.codeType" clearable>
          <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="">
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
          :data="codeList"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="30">
        </el-table-column>
        <el-table-column type="expand" >
          <template slot-scope="props">
            <pc-code :code="props.row"></pc-code>
          </template>
        </el-table-column>
        <el-table-column
            prop="runId"
            label="编号"
            width="50">
        </el-table-column>
        <el-table-column
            prop="userAccount"
            label="用户账号">
        </el-table-column>
        <el-table-column
            prop="userName"
            label="用户昵称">
        </el-table-column>
        <el-table-column
            prop="codeType"
            label="代码语言">
        </el-table-column>
        <el-table-column label="运行输入">
          <template slot-scope="scope">
            <span v-if="scope.row.runInput.length >= 12">
              {{ scope.row.runInput.slice(0, 12) }}...
            </span>
            <span v-if="scope.row.runInput.length < 12">
              {{ scope.row.runInput }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="运行输出">
          <template slot-scope="scope">
            <span v-if="scope.row.runError !== ''">
              <span v-if="scope.row.runError.length >= 12">
                {{ scope.row.runError.slice(0, 12) }}...
              </span>
              <span v-if="scope.row.runError.length < 12">
                {{ scope.row.runError.slice(0, 12) }}
              </span>
            </span>
            <span v-else>
              <span v-if="scope.row.runOutput.length >= 12">
                {{ scope.row.runOutput.slice(0, 12) }}...
              </span>
              <span v-if="scope.row.runOutput.length < 12">
                {{ scope.row.runOutput }}
              </span>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="运行状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.runError === ''" type="success">运行正常</el-tag>
            <el-tag v-if="scope.row.runError !== ''" type="danger" >运行错误</el-tag>
          </template>
        </el-table-column>
        <el-table-column
            prop="runCreateDate"
            label="创建时间">
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


    <el-dialog title="编辑代码" :visible.sync="updateCodeVisible" center destroy-on-close>
      <update-code-form
          :key="new Date().getTime()"
          :code-prop="Object.assign({}, this.codeProp)"
          @exit="exit"
          @reload="reload"></update-code-form>
    </el-dialog>

  </div>
</template>

<script>
import Prism from "prismjs";


import PcCode from "@/components/pc-code";
import UpdateCodeForm from "@/components/admin/codeManagement/UpdateCodeForm";
import moment from "moment";

export default {
  name: "RunManagement",
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
      types: [{
        value: 'java',
        label: 'Java'
      }, {
        value: 'python',
        label: 'Python'
      }, {
        value: 'cpp',
        label: 'C/C++'
      }],
      time: '',
      tableSelects: [],
      deleteDisabled: true,
      updateDisabled: true,
      codeList: [],
      total: 0,
      current: 1,
      size: 5,
      allDelete: false,
      updateCodeVisible: false,
      insertCodeVisible: false,
      codeProp: {},
      code: {
        userAccount: '',
        codeType: '',
        codTitle: '',
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
      await this.load();
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

      console.log(startDate, endDate);

      if (key != null && key.type === "click") {
        this.current = 1;
      }
      console.log(JSON.stringify(this.code));
      await this.$axios({
        method: "get",
        url: `${this.$url}/run/pageList`,
        params: {
          "userAccount": this.code.userAccount,
          "codeType": this.code.codeType,
          "startDate": startDate,
          "endDate": endDate,
          "current": this.current - 1,
        }
      }).then((response) => {
        if (response.data.flag === true) {

          let resp = response.data.data;
          this.codeList = resp.records;
          this.total = resp.count;

          console.log(resp);

          this.codeList.forEach((value) => {
            if (value.codeId === this.codeProp.codeId) {
              this.codeProp = value;
            }})
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
      this.$refs['runForm'].resetFields();
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
</style>