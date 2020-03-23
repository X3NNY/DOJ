# WIkI界面API文档

* `/api/wiki`

    @GET，获取wiki目录

    ```json
    [
        {
            "name": str, //名称 作为导航栏显示
            "title": str, //标题 作为界面显示
            "desc": str //描述
        },
    ]
    ```

* `/api/wiki/{name}`

    @GET，获取NAME目录下包含的blog

    ```json
    [
        {
            "bid": int,
            "up_vote": int, //获得的总赞数
            "down_vote": int, //获得的总踩数
            "state": int, //投票状态，0为未投票或未登陆，1代表赞，-1代表踩
            "title": str, //标题 作为界面显示
            "desc": str, //描述
            "name": str, //名称 作为导航栏显示
        },
    ]
    ```
