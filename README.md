# AIDL_service_braodcast


## Setting

build.grandle.kts(Module: app)

```xml
buildFeatures {
    aidl = true
}
```

Grandle 빌드 시스템에서 aidl을 enable 해야 됩니다.

![image](https://github.com/user-attachments/assets/e4ffa72a-2c53-4738-90bb-8989dcaec0d4)


AIDL폴더 내에 IMyAidlInterface.aidl 파일과 디렉토리 path를 생성합니다.

![image](https://github.com/user-attachments/assets/42cb50d8-4cce-4154-ae84-657f2a9f0e61)


```xml
package com.roh.my;

interface IMyAidlInterface {
   int add(int n1, int n2);
}
```

이 다음에 빌드를 진행하면 아래와 같이 interface 파일이 generate 됩니다

![image](https://github.com/user-attachments/assets/83f852a2-d68f-4390-aa76-ceb7ad618c73)

![image](https://github.com/user-attachments/assets/e8dbda1a-3d81-44a4-a0a9-01f42e3bb662)


IMyAidlInterface.aidl 파일을 빌드하면 [IMyAidlInterface.java](http://IMyAidlInterface.java) 파일로 생성됩니다. 

또한 inner class로 binder를 상속하는 stub 클래스가 생성되고 그 stub 클래스를 구현하면 됩니다

![image](https://github.com/user-attachments/assets/b1849f6f-cc2c-4894-b287-7bfabd156ee6)

함수 호출이 진행되는 쓰레드를 보장 할 수 없기에 Service를 사용하여 메인 쓰레드가 아닌 별도의 쓰레드에서 구현을 수행하도록 합니다.

### 이슈 사항

위 작업을 진행하고 나서 빌드된 파일을 바탕으로 서비스 프로젝트를 만들어야 아래와 같은 오류가 나타나지 않음

![image](https://github.com/user-attachments/assets/c125ad21-dd45-49be-9454-86bb884bfe56)

이는 이전에 build 프로젝트를 진행하고 생성된 자바 파일을 바탕으로 코드를 작성하고 있으며 clean 프로젝트를 통해 이전 빌드된 [IMyAidInterface.java](http://IMyAidInterface.java) 파일이 삭제되어 나타나는 오류이다

다시 빌드하게 되면 AIDL을 통해 java 파일이 생성되어 원래 화면으로 정상적으로 복구됩니다
