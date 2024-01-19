package com.uxm.blockchain.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.StaticArray2;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class SettlementContract extends Contract {
  public static final String BINARY = "60806040525f60015534801562000014575f80fd5b5060405162000952380380620009528339810160408190526200003791620002e7565b6200004561271082620003d7565b156200004f575f80fd5b600280546001600160a01b03191633179055604051620000769085908590602001620003f7565b60408051601f198184030181529190528051602090910120600355620000a060048360026200013b565b505f8181555b845181101562000130576040518060400160405280858381518110620000d057620000d06200047d565b602002602001015181526020015f81525060065f878481518110620000f957620000f96200047d565b6020908102919091018101516001600160a01b03168252818101929092526040015f208251815591015160019182015501620000a6565b505050505062000491565b82600281019282156200016c579160200282015b828111156200016c5782518255916020019190600101906200014f565b506200017a9291506200017e565b5090565b5b808211156200017a575f81556001016200017f565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f191681016001600160401b0381118282101715620001d357620001d362000194565b604052919050565b5f6001600160401b03821115620001f657620001f662000194565b5060051b60200190565b5f82601f83011262000210575f80fd5b81516020620002296200022383620001db565b620001a8565b8083825260208201915060208460051b8701019350868411156200024b575f80fd5b602086015b8481101562000269578051835291830191830162000250565b509695505050505050565b5f82601f83011262000284575f80fd5b604080519081016001600160401b0381118282101715620002a957620002a962000194565b8060405250806040840185811115620002c0575f80fd5b845b81811015620002dc578051835260209283019201620002c2565b509195945050505050565b5f805f8060a08587031215620002fb575f80fd5b84516001600160401b038082111562000312575f80fd5b818701915087601f83011262000326575f80fd5b81516020620003396200022383620001db565b82815260059290921b8401810191818101908b84111562000358575f80fd5b948201945b838610156200038d5785516001600160a01b03811681146200037d575f80fd5b825294820194908201906200035d565b918a0151919850909350505080821115620003a6575f80fd5b50620003b58782880162000200565b935050620003c7866040870162000274565b6080959095015193969295505050565b5f82620003f257634e487b7160e01b5f52601260045260245ffd5b500690565b604080825283519082018190525f906020906060840190828701845b828110156200043a5781516001600160a01b03168452928401929084019060010162000413565b505050838103828501528451808252858301918301905f5b81811015620004705783518352928401929184019160010162000452565b5090979650505050505050565b634e487b7160e01b5f52603260045260245ffd5b6104b3806200049f5f395ff3fe608060405260043610610084575f3560e01c80638da5cb5b116100575780638da5cb5b146100f957806399e0e37414610130578063a035b1fe14610177578063a6f2ae3a1461018b578063fb8f883514610193575f80fd5b806311da60b4146100885780631be20e9a1461009e5780636a27ce35146100c657806383197ef0146100e5575b5f80fd5b348015610093575f80fd5b5061009c6101a8565b005b3480156100a9575f80fd5b506100b360035481565b6040519081526020015b60405180910390f35b3480156100d1575f80fd5b506100b36100e036600461037c565b6102c7565b3480156100f0575f80fd5b5061009c6102dd565b348015610104575f80fd5b50600254610118906001600160a01b031681565b6040516001600160a01b0390911681526020016100bd565b34801561013b575f80fd5b5061016261014a366004610393565b60066020525f90815260409020805460019091015482565b604080519283526020830191909152016100bd565b348015610182575f80fd5b506100b35f5481565b61009c61030d565b34801561019e575f80fd5b506100b360015481565b335f908152600660209081526040918290208251808401909352805480845260019091015491830191909152158015906101f257505f81602001516001546101f091906103d4565b115b801561020057505f19600154105b610208575f80fd5b5f816020015160015461021b91906103d4565b82515f5461022c90612710906103ed565b610236919061040c565b610240919061040c565b60018054335f81815260066020526040808220909401929092559151929350909183156108fc0291849190818181858888f19350505050158015610286573d5f803e3d5ffd5b507f85d5b4b53d8e40700f7a1c9236d8f840dbca0a2531c7e841686af064e5d41906336004836040516102bb93929190610423565b60405180910390a15050565b600481600281106102d6575f80fd5b0154905081565b6002546001600160a01b0316331480156102f757505f5447105b6102ff575f80fd5b6002546001600160a01b0316ff5b345f5414801561031f57505f19600154105b610327575f80fd5b6001805f828254610338919061046a565b90915550505f546040517fb1c1b3fd2a70c69d5f7edca034be95d6e920bceae3e29ca2b3abd47bcef9d85f91610372913391600491610423565b60405180910390a1565b5f6020828403121561038c575f80fd5b5035919050565b5f602082840312156103a3575f80fd5b81356001600160a01b03811681146103b9575f80fd5b9392505050565b634e487b7160e01b5f52601160045260245ffd5b818103818111156103e7576103e76103c0565b92915050565b5f8261040757634e487b7160e01b5f52601260045260245ffd5b500490565b80820281158282048414176103e7576103e76103c0565b6001600160a01b0384168152608081016020808301855f5b60028110156104585781548352918301916001918201910161043b565b50505050826060830152949350505050565b808201808211156103e7576103e76103c056fea264697066735822122098921c2c5bf358801f032f0e5907eaf55db4862b6f150a2c91d4e42a1528326a64736f6c63430008170033";

  public static final String FUNC_BUY = "buy";

  public static final String FUNC_COPYRIGHTHOLDERS = "copyrightHolders";

  public static final String FUNC_CUMULATIVESALES = "cumulativeSales";

  public static final String FUNC_DESTROY = "destroy";

  public static final String FUNC_KECCAK256HASH = "keccak256Hash";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_PRICE = "price";

  public static final String FUNC_SETTLE = "settle";

  public static final String FUNC_SONGCID = "songCid";

  public static final Event LOGBUYERINFO_EVENT = new Event("logBuyerInfo",
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<StaticArray2<Bytes32>>() {}, new TypeReference<Uint256>() {}));
  ;

  public static final Event LOGRECIEVERINFO_EVENT = new Event("logRecieverInfo",
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<StaticArray2<Bytes32>>() {}, new TypeReference<Uint256>() {}));
  ;

  @Deprecated
  protected SettlementContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected SettlementContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected SettlementContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected SettlementContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static List<LogBuyerInfoEventResponse> getLogBuyerInfoEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGBUYERINFO_EVENT, transactionReceipt);
    ArrayList<LogBuyerInfoEventResponse> responses = new ArrayList<LogBuyerInfoEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      LogBuyerInfoEventResponse typedResponse = new LogBuyerInfoEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.songCid = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
      typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static LogBuyerInfoEventResponse getLogBuyerInfoEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGBUYERINFO_EVENT, log);
    LogBuyerInfoEventResponse typedResponse = new LogBuyerInfoEventResponse();
    typedResponse.log = log;
    typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
    typedResponse.songCid = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
    typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
    return typedResponse;
  }

  public Flowable<LogBuyerInfoEventResponse> logBuyerInfoEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getLogBuyerInfoEventFromLog(log));
  }

  public Flowable<LogBuyerInfoEventResponse> logBuyerInfoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(LOGBUYERINFO_EVENT));
    return logBuyerInfoEventFlowable(filter);
  }

  public static List<LogRecieverInfoEventResponse> getLogRecieverInfoEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(LOGRECIEVERINFO_EVENT, transactionReceipt);
    ArrayList<LogRecieverInfoEventResponse> responses = new ArrayList<LogRecieverInfoEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      LogRecieverInfoEventResponse typedResponse = new LogRecieverInfoEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.reciever = (String) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.songCid = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
      typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static LogRecieverInfoEventResponse getLogRecieverInfoEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(LOGRECIEVERINFO_EVENT, log);
    LogRecieverInfoEventResponse typedResponse = new LogRecieverInfoEventResponse();
    typedResponse.log = log;
    typedResponse.reciever = (String) eventValues.getNonIndexedValues().get(0).getValue();
    typedResponse.songCid = (List<byte[]>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
    typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
    return typedResponse;
  }

  public Flowable<LogRecieverInfoEventResponse> logRecieverInfoEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getLogRecieverInfoEventFromLog(log));
  }

  public Flowable<LogRecieverInfoEventResponse> logRecieverInfoEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(LOGRECIEVERINFO_EVENT));
    return logRecieverInfoEventFlowable(filter);
  }

  public RemoteFunctionCall<TransactionReceipt> buy(BigInteger weiValue) {
    final Function function = new Function(
        FUNC_BUY,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function, weiValue);
  }

  public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> copyrightHolders(String param0) {
    final Function function = new Function(FUNC_COPYRIGHTHOLDERS,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
        new Callable<Tuple2<BigInteger, BigInteger>>() {
          @Override
          public Tuple2<BigInteger, BigInteger> call() throws Exception {
            List<Type> results = executeCallMultipleValueReturn(function);
            return new Tuple2<BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(),
                (BigInteger) results.get(1).getValue());
          }
        });
  }

  public RemoteFunctionCall<BigInteger> cumulativeSales() {
    final Function function = new Function(FUNC_CUMULATIVESALES,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<TransactionReceipt> destroy() {
    final Function function = new Function(
        FUNC_DESTROY,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<byte[]> keccak256Hash() {
    final Function function = new Function(FUNC_KECCAK256HASH,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    return executeRemoteCallSingleValueReturn(function, byte[].class);
  }

  public RemoteFunctionCall<String> owner() {
    final Function function = new Function(FUNC_OWNER,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<BigInteger> price() {
    final Function function = new Function(FUNC_PRICE,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<TransactionReceipt> settle() {
    final Function function = new Function(
        FUNC_SETTLE,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<byte[]> songCid(BigInteger param0) {
    final Function function = new Function(FUNC_SONGCID,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    return executeRemoteCallSingleValueReturn(function, byte[].class);
  }

  @Deprecated
  public static SettlementContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return new SettlementContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static SettlementContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new SettlementContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static SettlementContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return new SettlementContract(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static SettlementContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new SettlementContract(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<SettlementContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
            org.web3j.abi.datatypes.Address.class,
            org.web3j.abi.Utils.typeMap(_addresses, org.web3j.abi.datatypes.Address.class)),
        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
            org.web3j.abi.datatypes.generated.Uint256.class,
            org.web3j.abi.Utils.typeMap(_proportions, org.web3j.abi.datatypes.generated.Uint256.class)),
        new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
            org.web3j.abi.datatypes.generated.Bytes32.class,
            org.web3j.abi.Utils.typeMap(_songCid, org.web3j.abi.datatypes.generated.Bytes32.class)),
        new org.web3j.abi.datatypes.generated.Uint256(_price)));
    return deployRemoteCall(SettlementContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
  }

  public static RemoteCall<SettlementContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
            org.web3j.abi.datatypes.Address.class,
            org.web3j.abi.Utils.typeMap(_addresses, org.web3j.abi.datatypes.Address.class)),
        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
            org.web3j.abi.datatypes.generated.Uint256.class,
            org.web3j.abi.Utils.typeMap(_proportions, org.web3j.abi.datatypes.generated.Uint256.class)),
        new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
            org.web3j.abi.datatypes.generated.Bytes32.class,
            org.web3j.abi.Utils.typeMap(_songCid, org.web3j.abi.datatypes.generated.Bytes32.class)),
        new org.web3j.abi.datatypes.generated.Uint256(_price)));
    return deployRemoteCall(SettlementContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<SettlementContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
            org.web3j.abi.datatypes.Address.class,
            org.web3j.abi.Utils.typeMap(_addresses, org.web3j.abi.datatypes.Address.class)),
        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
            org.web3j.abi.datatypes.generated.Uint256.class,
            org.web3j.abi.Utils.typeMap(_proportions, org.web3j.abi.datatypes.generated.Uint256.class)),
        new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
            org.web3j.abi.datatypes.generated.Bytes32.class,
            org.web3j.abi.Utils.typeMap(_songCid, org.web3j.abi.datatypes.generated.Bytes32.class)),
        new org.web3j.abi.datatypes.generated.Uint256(_price)));
    return deployRemoteCall(SettlementContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<SettlementContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
            org.web3j.abi.datatypes.Address.class,
            org.web3j.abi.Utils.typeMap(_addresses, org.web3j.abi.datatypes.Address.class)),
        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
            org.web3j.abi.datatypes.generated.Uint256.class,
            org.web3j.abi.Utils.typeMap(_proportions, org.web3j.abi.datatypes.generated.Uint256.class)),
        new org.web3j.abi.datatypes.generated.StaticArray2<org.web3j.abi.datatypes.generated.Bytes32>(
            org.web3j.abi.datatypes.generated.Bytes32.class,
            org.web3j.abi.Utils.typeMap(_songCid, org.web3j.abi.datatypes.generated.Bytes32.class)),
        new org.web3j.abi.datatypes.generated.Uint256(_price)));
    return deployRemoteCall(SettlementContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
  }

  public static class LogBuyerInfoEventResponse extends BaseEventResponse {
    public String buyer;

    public List<byte[]> songCid;

    public BigInteger amount;
  }

  public static class LogRecieverInfoEventResponse extends BaseEventResponse {
    public String reciever;

    public List<byte[]> songCid;

    public BigInteger amount;
  }
}
