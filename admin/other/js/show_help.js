 var formData = new FormData();
 new Vue({
     el: "#app",
     data: {
         data: [],
         page: 1,
         num: 8,
         total: 1,
         categorys: ["全部", "发布中", "进行中", "待付款", "已完成"],
         statusColor: ["#00a", "#0a0", "#900", "#a0a"],
         categoryIndex: 0,
         dialogShow: false,
         curData: {},

     },
     mounted: function() {
         this.$nextTick(function() {
             this.getData();
         })
     },
     methods: {
         getData: function() {
             var _this = this;
             axios.get(serverAddress + "admin/help/all")
                 .then(function(res) {
                     var result = res.data;
                     if (result.code == 0) {
                         _this.data = result.data
                         _this.total = parseInt(_this.data.length / _this.num + 0.9);
                         // console.log(_this.data, _this.total);
                     } else {
                         alert(result.msg);
                     }
                 })
         },
         helpStatus: function(status, color = 1) {
             if (color == 1)
                 return "<font color=" + this.statusColor[status] + ">" + Array("发布中", "进行中", "未付款", "已完成")[status] + "</font>";
             else
                 return Array("发布中", "进行中", "未付款", "已完成")[status];
         },
         dataFormate: function(date) {
             return new Date(date).toLocaleDateString();
         },
         del: function(index) {
             if (confirm("确定要删除吗?") == true) {
                 // console.log(index);
                 var _this = this;
                 axios.delete(serverAddress + "admin/help/" + this.data[index].helpId)
                     .then(function(res) {
                         var result = res.data;
                         if (result.code == 0) {
                             alert("删除成功");
                             _this.data.splice(index, 1)
                         } else {
                             alert(result.msg);
                         }
                     })
             }
         },
         cat: function() {
             var catData = [];
             if (this.categoryIndex == 0) {
                 this.total = parseInt(this.data.length / this.num + 0.9);
                 return this.data;
             }

             for (var i = 0; i < this.data.length; i++) {
                 if (this.data[i].status == this.categoryIndex - 1)
                     catData.push(this.data[i]);
             }
             console.log(catData);
             this.total = parseInt(catData.length / this.num + 0.9);
             return catData;
         }

     }
 })