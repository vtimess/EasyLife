    var formData = new FormData();
    new Vue({
        el: "#app",
        data: {
            data: [],
            page: 1,
            num: 8,
            total: "",
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
                axios.get(serverAddress + "/admin/user/all")
                    .then(function(res) {
                        var result = res.data;
                        console.log(result);
                        if (result.code == 0) {
                            _this.data = result.data
                            _this.total = parseInt(_this.data.length / _this.num + 0.9);
                            console.log(_this.data, _this.total);
                        } else {
                            alert(result.msg);
                        }
                    })
            },
            del: function(index) {
                if (confirm("确定要删除吗?") == true) {
                    console.log(index);
                    var _this = this;
                    axios.delete(serverAddress + "admin/user/" + this.data[index].userId)
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


            }

        }
    })