<template>
  <div>
    <navbar title="发布帖子"></navbar>
    <el-card class="box-card">
      <van-field label="标题"
                 v-model="titleStr"
                 placeholder="添加标题"
      />
      <van-field label="正文"
                 v-model="contentStr"
                 rows="2"
                 :autosize="size"
                 type="textarea"
                 placeholder="添加正文"
                 show-word-limit
      />
    </el-card>
    <div class="btn" @click="submit">发布</div>
  </div>
</template>

<script>
import Navbar from "@/components/navbar";
import request from "@/req";
import {Toast} from "vant";

export default {
  name: "PushArticle",
  components: {Navbar},
  props: {},
  data() {
    return {
      contentStr: '',
      titleStr: '',
      size: {maxHeight: 500, minHeight: 300},
    }
  },
  methods: {
    submit() {
      if (this.contentStr===''||this.titleStr===''){
        Toast.fail('请完整输入')
        return
      }
      request.post('/forum/insertArticle', {
        uid: this.$getUser().uid,
        content: this.contentStr,
        title: this.titleStr
      }).then(res => {
        Toast('发布成功')
        this.$router.go(-1)

      })
    }
  },
  created() {

  }
}
</script>

<style scoped>
</style>
