    var formData = new FormData();
    new Vue({
        el: "#app",
        data: {
            data: [],
            page: 1,
            num: 8,
            total: "",
            rechargeIndex: -1,
            money: 0
        },
        mounted: function() {
            this.$nextTick(function() {
                this.getData();
            })
        },
        methods: {
            getData: function() {
                var _this = this;
                axios.get(serverAddress + "/admin/wallet")
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
            recharge: function(index, userId) {
                var _this = this;
                var formData = new FormData();
                formData.append("money", this.money);
                axios.post(serverAddress + "/admin/wallet/" + userId, formData)
                    .then(function(res) {
                        var result = res.data;
                        console.log(result);
                        if (result.code == 0) {
                            _this.data[index].money = result.data.money;
                            alert("充值成功");
                            console.log(result.data);
                            _this.money = 0;
                        } else {
                            alert(result.msg);
                            _this.money = 0;
                        }
                    })
            }
        }
    })