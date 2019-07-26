package com.ldz.biz;

import com.ldz.util.commonUtil.RSAUtils;

public class Test {


    public static void main(String[] args) throws Exception {
       /* String apiKey = "slu";
        String secretKey = "sly";
        PushKeyPair keyPair = new PushKeyPair(apiKey, secretKey);
        BaiduPushClient pushClient = new BaiduPushClient(keyPair, BaiduPushConstants.CHANNEL_REST_URL);
        pushClient.setChannelLogHandler(yunLogEvent -> {
            System.out.println(yunLogEvent.getMessage());
        });*/
        String s = RSAUtils.decryptWithRSA("n0imMSAJY3XkZSqS99j76C6QOUGj0Bikxk1+Ggtd6hwzqlmwHcYAN9kqXrdnUuFUeTxKkMwVbO2KyKTgfBT9+XorqYFnpapJ6f0pYv7b+Db+Mf20WTzFQja5CpnSusarVPW/qn1M1shgqpREwAuQ9lf70QySFeOEGJtmbxqG3hY2CTp0D1PSV1WAAbKf3UCT4L7VOWJDkU4jUODQsvXWdEyuZQdmWciS4TPSdP/TAKTeEdfnEiZFXRm6SdkMM95m7Xr2KpBj3SYiiuSRE4zNjkvYnlMD764/VfGMhu67uWWQU4kNu7K6Kv9wHtGd7YFnTXU6QMEg0Fu9hPq6MUJPOFpNIhyLMMl8LlLvKJl3GkqQack2BkBI/pKYzAfaROanRyC46bHpR7XO1kavd3UchIQu0yra1d59myu2Sw/h0XWn7MIRrQ57BWPzwDyiCHsxyZiOPdCabkQQ2OP9NERNjmJhkT1l81nlcz2qDH88Mgzt17MDoljO9Q8fkxDg+6e5TAf93UKA/D+Lm31wMXM4YWjKEhAktSUsspQOMw0kTwXYMUU6pQKzCYuAz9P+ce2E1nQmxaITs93MtFzKlAkdxCcw9U5CJSq3jz/giTS/Fd/yOKQnBxYAjurDSVauKNPPUrklk4VCwQjnHVusaHYPuypTB3mge8ytnhoWPxuMNoC7Qkx0ZjkBZFPRYeM+mHcKleRP4oje6iULe/7K9lPU25h2O5u1TOhnH0YaInfS49G34jxejrBSU3hCXknGCnQnyO9uLyiRS79tSymbX6mHHaSIqGU4iHZwZwE0NfoMdYVZD+lJaKZXg/BXmnn/UUK4CoHq9UxpTWU4iY1Zxgh7RAKTgIe3qWt3P+z2rQho0HR9JlufzGrzcc0A/gtU9uEf6NHiBAhtYfbeGhBk3aGBJyunIPsXtJzW3iWpaMUDtmR/64F2ELrEvRWWgsj4c2drSZBpLgBT+1uGrVJeUocdMQ7dynpuZfZFoFwqOTD53Y2jE8SCojee1mP95/ge7SMs");
        System.out.println(s);

    }


}
