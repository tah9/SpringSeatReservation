<template>
  <div>
    <navbar title="帖子详情"></navbar>
    <el-card class="box-card">
      <div style="font-weight: bold">{{ item.title }}</div>
      <div style="margin-top: 1em">{{ item.content }}</div>
    </el-card>
    <el-divider>评论</el-divider>
    <el-card class="box-card" v-for="item in commentRows">
      <div>
        <div style="display: flex;justify-content: space-between;margin-bottom: 4px">
          <div>{{ item.username }}</div>
          <div>{{ $formatTime(item.datetime) }}</div>
        </div>
        <div> {{ item.content }}</div>
      </div>
    </el-card>

    <van-field label="回复" @keyup.enter="submit"
               v-model="contentStr" style="position: fixed;bottom: 0"
               placeholder="添加回复"
    />
  </div>
</template>

<script>
import Navbar from "@/components/navbar";
import request from "@/req";
import {Toast} from "vant";

export default {
  name: "ForumInfo",
  components: {Navbar},
  props: {},
  data() {
    return {
      item: null,
      contentStr: '',
      commentRows: null
    }
  },
  methods: {
    submit() {
      if(this.contentStr===''||this.item===null||this.commentRows===null){
        Toast.fail('请完整输入')
        return
      }
      request.post('/forum/insertComment', {
        aid: this.item.id,
        uid: this.$getUser().uid,
        content: this.contentStr,
      }).then(res => {
        Toast('发布成功')
        this.contentStr = ''
        this.update()
      })
    },
    update() {
      request.post('/forum/getComment', {
        aid: this.item.id
      }).then(res => {
        this.commentRows = res.rows
      })
    }
  },
  created() {
    this.item = JSON.parse(localStorage.getItem('forum'))
    this.update()
  }
}
</script>

<style scoped>


</style>
