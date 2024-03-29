/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mingle.pear.web;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthChainId;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mingle
 */
@RequestMapping("/blockchain")
@RestController
public class BlockchainController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource private Web3j web3j;

    /**
     * 获取最新的区块号
     */
    @GetMapping("/blockNumber")
    public BigInteger doGetLatestBlockNumber() throws Exception {
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        logger.info("BlockNumber: {}", blockNumber);
        return blockNumber;
    }

    /**
     * 获取所有账户
     */
    @GetMapping("/accounts")
    public List<String> doGetAllAccounts() throws Exception {
        EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
        List<String> accounts = ethAccounts.getAccounts();
        logger.info("Accounts: {}", accounts);
        return accounts;
    }

    /**
     * 获取Gas价格
     */
    @GetMapping("/gasPrice")
    public BigInteger doGetEthGasPrice() throws Exception {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        BigInteger gasPrice = ethGasPrice.getGasPrice();
        logger.info("Ethereum Gas Price: {}", gasPrice);
        return gasPrice;
    }

    /**
     * 获取链Id
     */
    @GetMapping("/chainId")
    public BigInteger doGetChainId() throws Exception {
        EthChainId ethChainId = web3j.ethChainId().sendAsync().get();
        BigInteger chainId = ethChainId.getChainId();
        logger.info("Ethereum Chain Id: {}", chainId);
        return chainId;
    }

    /**
     * 获取CoinBase
     */
    @GetMapping("/coinbase")
    public String doGetCoinBase() throws Exception {
        EthCoinbase ethCoinbase = web3j.ethCoinbase().sendAsync().get();
        String coinBase = ethCoinbase.getAddress();
        logger.info("Ethereum CoinBase Address: {}", coinBase);
        return coinBase;
    }

    /**
     * 根据区块号获取区块信息
     */
    @GetMapping("/blockInfo")
    public String doGetAll(@RequestParam(value = "blockNumber") Long blockNumber) throws Exception {
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        EthBlock.Block block = ethBlock.getBlock();
        Gson gson = new Gson();
        String info = gson.toJson(block);
        logger.info(info);
        return info;
    }

    /**
     * 根据区块号获取所有交易
     */
    @GetMapping("/txByBlockNumber")
    public String doGetTransactionInfoByBlockNumber(@RequestParam(value = "blockNumber") Long blockNumber)
            throws Exception {
        DefaultBlockParameterNumber defaultBlockParameterNumber = new DefaultBlockParameterNumber(blockNumber);
        EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameterNumber, true).sendAsync().get();
        List<EthBlock.TransactionResult> transactionResults = ethBlock.getBlock().getTransactions();
        List<Transaction> txInfos = new ArrayList<>();

        transactionResults.forEach(txInfo -> {
            Transaction transaction = (Transaction) txInfo;
            txInfos.add(transaction);
        });
        Gson gson = new Gson();
        String transactionInfo = gson.toJson(txInfos);
        logger.info(transactionInfo);
        return transactionInfo;
    }

    /**
     * @description 根据交易哈希值获取交易信息
     */
    @GetMapping("/txByHash")
    public String doGetTransactionInfoByHash(@RequestParam(value = "txHash") String txHash) throws Exception {
        EthTransaction transaction = web3j.ethGetTransactionByHash(txHash).sendAsync().get();
        Optional<Transaction> optionalTransaction = transaction.getTransaction();
        StringBuilder txInfo = new StringBuilder();
        if (optionalTransaction.isPresent()) {
            Transaction transactionInfo = optionalTransaction.get();
            Gson gson = new Gson();
            txInfo.append(gson.toJson(transactionInfo));
        }
        logger.info(txInfo.toString());
        return txInfo.toString();
    }
}
