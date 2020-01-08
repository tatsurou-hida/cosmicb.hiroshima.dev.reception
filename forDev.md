## 環境
~~Eclipse 2019-12 http://www.eclipse.org/~~

Pleiades All in One 2019-12 (Eclipse 日本語化) http://mergedoc.sourceforge.jp/

Java8

Spring Boot (STS4 for Eclipse)

Maven

MongoDB Atlas https://www.mongodb.com/cloud/atlas

Bootstrap4 https://getbootstrap.com/

JQuery3.4.1 https://jquery.com/

現環境との対応

変更前 | |  変更後
--- | --- | ---
Visual Studio Code | エディタ／IDE | Eclipse
Struts2 | webフレームワーク | SpringFramework (Spring Boot)
JSP | テンプレートエンジン | Tymeleaf
MySQL | データベース | MongoDB

## エンティティ

`_id`はMongoDBによって自動的に付与されるドキュメントのIDです。

```javascript
{ "_id" : ObjectId("55b0a23cb32d11a401f7dd7a"), "name" : "taro" }
```

##### 来訪履歴 office_visit (後ほど変更します）

キー | 論理名 | 型
--- | --- | ---
_id | (Object ID) | object id
visitor_name | 来訪者名 | string
visitor_org | 来訪者所属 | string
visited_at | 来訪時刻 | date
left_at | 退出時刻 | date

## 画面
##### 画面A
##### 画面B

---
以下雑記

##### Pleiades...の解凍に7-Zipが必要
（パス長制限のためWin標準やLhaplusなどではエラーがでる）

https://ja.osdn.net/projects/sevenzip/downloads/70662/7z1900-x64.exe/

##### Pleiades All in One Eclipse 2019-12
Windows 64bit Full Edition Java

https://mergedoc.osdn.jp/


##### STS4

Eclipseマーケットプレイス → STSで検索

Spring Tools 4 - for Spring Boot (aka Spring Tool Suite 4) 4.5.0.RELEASE

インストール済みでした

新規 → その他 → Spring Boot → Springスターター・プロジェクト

型: Maven

Javaバージョン: 8

Spring Boot バージョン: 2.2.2



##### 依存関係

開発ツール - Spring Boot DevTools

NoSQL - Spring Data MongoDB

テンプレート・エンジン - Thymeleaf

Web - Web

https://qiita.com/ketman55/items/16d1e491a5a493f71c92


##### Maven

Springスターター・プロジェクトでMavenを選択していればOK

pom.xmlが作成されていればOK


##### MongoDB

Springスターター・プロジェクトでMongoDBへの依存を設定していればOK

pom.xmlにspring-boot-starter-data-mongodbが記載されているか確認


##### formatter
→未定

クラス作成
https://eng-entrance.com/java-springboot#SpringBoot-2


## PlantUML

UMLをコードで記述

##### Eclipseプラグイン

Eclipseマーケットプレイス → PlantUMLで検索

PlantUML plugin 1.1.22



##### Graphviz 2.38 Stable Release

Graphvizは別途インストールしないとグラフデータ構造の可視化ができない

（シンプルなシーケンス図しか書けない）のでこれも追加する

Eclipse再起動が必要

https://graphviz.gitlab.io/download/



##### Pegmatite

GitHub上で確認するためのChrome拡張

GitHub上でのみ動作する

ダブルクリックでコードと画像が入れ替わる

https://dev.classmethod.jp/tool/chrome-extension-plantuml-in-github-markdown/
