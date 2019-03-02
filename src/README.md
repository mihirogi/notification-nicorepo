
## プロジェクトの準備
### ローカルで動かす場合

`set_env.sh`を編集する
```sh
export DISCORD_WEBHOOK_ENDPOINT=This_attribute_is_Webhook_URL_of_Discord
export NICONICO_MAILADDRESS=This_attribute_is_login_mailaddress_of_Niconicodouga
export NICONICO_PASSWORD=This_attribute_is_login_password_of_Niconicodouga
```

環境変数を設定する

`source ./set_env.sh`

**IntelliJ for Macの場合は、以下のコマンドで立ち上げないと、環境変数が引き継がれません**

`open -a Path to IntelliJ`