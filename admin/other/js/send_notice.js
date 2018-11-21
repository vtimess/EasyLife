   new Vue({
       el: "#app",
       data: {
           message: "",
       },
       methods: {
           send: function() {
               var formData = new FormData();
               formData.append("msg", this.message);
               var _this = this;
               axios.post(serverAddress + "admin/notice", formData)
                   .then(function(res) {
                       var result = res.data;
                       if (result.code == 0) {
                           alert("发送成功")
                       } else {
                           alert(result.msg)

                       }
                       _this.message = ""
                   })
           }

       }
   })