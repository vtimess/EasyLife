    var formData = new FormData();
    new Vue({
        el: "#app",
        data: {
            form: {
                phoneNumber: "",
                password: ""
            },
        },
        methods: {
            submit: function() {
                formData.append("phoneNumber", this.form.phoneNumber);
                formData.append("password", this.form.password);
                var _this = this;
                axios.post(serverAddress + "admin/user", formData)
                    .then(function(res) {
                        var result = res.data;
                        if (result.code == 0) {
                            alert("成功")
                            console.log(result.data)
                            _this.reset()

                        } else {
                            alert(result.msg)
                            formData = new FormData()
                        }
                    });
            },
            reset: function() {
                this.form.phoneNumber = ""
                this.form.password = ""
                formData = new FormData()
            },
            tirggerFile: function($event) {
                var file = event.target.files[0]
                formData.append("image", file)
                console.log(formData)
            }
        }
    })