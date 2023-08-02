## Solace PubSub+ based Spring based Simulator POC for Image download from remote server

## Author : Shreejit Panchal

## Access a PubSub+ Service
The Spring Tutorials require that you have access to a PubSub+ Service. You can quickly set one up for FREE by following [these instructions](https://solace.com/try-it-now/)

--- Simulator Architecture ---
![Overall-Architecture.png](resources/Overall-Architecture.png)

--- Follow below steps to provision Solace PubSub+ Cloud Services for this Simulator

-- Step 1: Create 2 Solace services with name Intranet and Internet as below:
![solace-step1.png](resources/solace-step1.png)

-- Step 2: Configure and create Queue EDA.POC.IMAGE.PROCESSOR.RESPONSE in Intranet Solace services via PubSub+ Broker Manager UI:
![solace-step2.png](resources/solace-step2.png)

-- Step 3: Configure Topic Subscription as below to Queue EDA.POC.IMAGE.PROCESSOR.RESPONSE in Intranet Solace services via PubSub+ Broker Manager UI:
![solace-step3.png](resources/solace-step3.png)

-- Step 4: Configure and create Queue EDA.POC.IMAGE.PROCESSOR.REQUEST in Internet Solace services via PubSub+ Broker Manager UI:
![solace-step4.png](resources/solace-step4.png)

-- Step 5: Configure Topic Subscription as below to Queue EDA.POC.IMAGE.PROCESSOR.REQUEST in Internet Solace services via PubSub+ Broker Manager UI:
![solace-step5.png](resources/solace-step5.png)

-- Step 6: Configure Bridge between Intranet and Internet service and add Bridge Subscription to individual service as below:
![solace-step6.png](resources/solace-step6.png)
![solace-step7.png](resources/solace-step7png)