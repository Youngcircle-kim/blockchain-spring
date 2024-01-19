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
public class SettlementContractExtra extends Contract {
    public static final String BINARY = "60806040525f60015534801562000014575f80fd5b506040516200104b3803806200104b833981016040819052620000379162000352565b620000456127108262000442565b156200004f575f80fd5b600280546001600160a01b0319163317905560405162000076908590859060200162000462565b60408051601f198184030181529190528051602090910120600355620000a06004836002620001a6565b505f8181555b845181101562000130576040518060400160405280858381518110620000d057620000d0620004e8565b602002602001015181526020015f81525060065f878481518110620000f957620000f9620004e8565b6020908102919091018101516001600160a01b03168252818101929092526040015f208251815591015160019182015501620000a6565b506002546001600160a01b03165f908152600660205260409020546200019c5760405162461bcd60e51b815260206004820152601560248201527f6e6f74206120436f70797269676874486f6c6465720000000000000000000000604482015260640160405180910390fd5b50505050620004fc565b8260028101928215620001d7579160200282015b82811115620001d7578251825591602001919060010190620001ba565b50620001e5929150620001e9565b5090565b5b80821115620001e5575f8155600101620001ea565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f191681016001600160401b03811182821017156200023e576200023e620001ff565b604052919050565b5f6001600160401b03821115620002615762000261620001ff565b5060051b60200190565b5f82601f8301126200027b575f80fd5b81516020620002946200028e8362000246565b62000213565b8083825260208201915060208460051b870101935086841115620002b6575f80fd5b602086015b84811015620002d45780518352918301918301620002bb565b509695505050505050565b5f82601f830112620002ef575f80fd5b604080519081016001600160401b0381118282101715620003145762000314620001ff565b80604052508060408401858111156200032b575f80fd5b845b81811015620003475780518352602092830192016200032d565b509195945050505050565b5f805f8060a0858703121562000366575f80fd5b84516001600160401b03808211156200037d575f80fd5b818701915087601f83011262000391575f80fd5b81516020620003a46200028e8362000246565b82815260059290921b8401810191818101908b841115620003c3575f80fd5b948201945b83861015620003f85785516001600160a01b0381168114620003e8575f80fd5b82529482019490820190620003c8565b918a015191985090935050508082111562000411575f80fd5b5062000420878288016200026b565b935050620004328660408701620002df565b6080959095015193969295505050565b5f826200045d57634e487b7160e01b5f52601260045260245ffd5b500690565b604080825283519082018190525f906020906060840190828701845b82811015620004a55781516001600160a01b0316845292840192908401906001016200047e565b505050838103828501528451808252858301918301905f5b81811015620004db57835183529284019291840191600101620004bd565b5090979650505050505050565b634e487b7160e01b5f52603260045260245ffd5b610b41806200050a5f395ff3fe6080604052600436106100a5575f3560e01c80638da5cb5b116100625780638da5cb5b1461015857806399e0e3741461018f578063a035b1fe146101d6578063a6f2ae3a146101ea578063ed49dde5146101f2578063fb8f883514610226575f80fd5b806311da60b4146100a95780631be20e9a146100bf5780633e62d04a146100e75780634f06a7a2146101065780636a27ce351461012557806383197ef014610144575b5f80fd5b3480156100b4575f80fd5b506100bd61023b565b005b3480156100ca575f80fd5b506100d460035481565b6040519081526020015b60405180910390f35b3480156100f2575f80fd5b506100bd6101013660046109ac565b61035a565b348015610111575f80fd5b506100bd6101203660046109ce565b610508565b348015610130575f80fd5b506100d461013f366004610a05565b6108e0565b34801561014f575f80fd5b506100bd6108f6565b348015610163575f80fd5b50600254610177906001600160a01b031681565b6040516001600160a01b0390911681526020016100de565b34801561019a575f80fd5b506101c16101a93660046109ac565b60066020525f90815260409020805460019091015482565b604080519283526020830191909152016100de565b3480156101e1575f80fd5b506100d45f5481565b6100bd610926565b3480156101fd575f80fd5b5061017761020c3660046109ac565b60076020525f90815260409020546001600160a01b031681565b348015610231575f80fd5b506100d460015481565b335f9081526006602090815260409182902082518084019093528054808452600190910154918301919091521580159061028557505f81602001516001546102839190610a30565b115b801561029357505f19600154105b61029b575f80fd5b5f81602001516001546102ae9190610a30565b82515f546102bf9061271090610a49565b6102c99190610a68565b6102d39190610a68565b60018054335f81815260066020526040808220909401929092559151929350909183156108fc0291849190818181858888f19350505050158015610319573d5f803e3d5ffd5b507f85d5b4b53d8e40700f7a1c9236d8f840dbca0a2531c7e841686af064e5d419063360048360405161034e93929190610a7f565b60405180910390a15050565b806001600160a01b0316336001600160a01b031663075461726040518163ffffffff1660e01b8152600401602060405180830381865afa1580156103a0573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906103c49190610ac6565b6001600160a01b03161461041f5760405162461bcd60e51b815260206004820152601860248201527f4e6f74204e465420636f6e7472616374206d696e74657221000000000000000060448201526064015b60405180910390fd5b6001600160a01b0381165f908152600660205260409020546104835760405162461bcd60e51b815260206004820152601760248201527f4e6f74206120636f7079726967687420686f6c646572210000000000000000006044820152606401610416565b6001600160a01b038181165f9081526007602052604090205416156104e05760405162461bcd60e51b8152602060048201526013602482015272416c726561647920726567697374657265642160681b6044820152606401610416565b6001600160a01b03165f90815260076020526040902080546001600160a01b03191633179055565b336001600160a01b031660075f336001600160a01b031663075461726040518163ffffffff1660e01b8152600401602060405180830381865afa158015610551573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105759190610ac6565b6001600160a01b03908116825260208201929092526040015f205416146105de5760405162461bcd60e51b815260206004820152601960248201527f6e6f7420612070726f706572204e465420436f6e7472616374000000000000006044820152606401610416565b604051627eeac760e11b81526001600160a01b03821660048201525f6024820152600190339062fdd58e90604401602060405180830381865afa158015610627573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061064b9190610ae1565b101561068b5760405162461bcd60e51b815260206004820152600f60248201526e3737ba10309027232a1037bbb732b960891b6044820152606401610416565b604080516001600160a01b038085165f908152600660208181528583206080860187528054868801908152600191820154606088015286529387168352908152908490208451808601865281548152920154828201528083019190915282518084018085526316d091b560e21b9052925191929182913391635b4246d491604480860192908187030181865afa158015610727573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061074b9190610ae1565b8351516107589190610a30565b8152600180546020928301526001600160a01b0386165f908152600683526040908190208451815593830151939091019290925581518083019092528201515182515182916107a691610af8565b8152600180546020928301526001600160a01b0385165f908152600683526040902083518155928201519281019290925590548251909101511015610854578051602001516001546001600160a01b038516916108fc916108079190610a30565b8351515f546108199061271090610a49565b6108239190610a68565b61082d9190610a68565b6040518115909202915f818181858888f19350505050158015610852573d5f803e3d5ffd5b505b600154602080830151015110156108db576001600160a01b0383166108fc82600160200201516020015160015461088b9190610a30565b6020840151515f546108a09061271090610a49565b6108aa9190610a68565b6108b49190610a68565b6040518115909202915f818181858888f193505050501580156108d9573d5f803e3d5ffd5b505b505050565b600481600281106108ef575f80fd5b0154905081565b6002546001600160a01b03163314801561091057505f5447105b610918575f80fd5b6002546001600160a01b0316ff5b345f5414801561093857505f19600154105b610940575f80fd5b6001805f8282546109519190610af8565b90915550505f546040517fb1c1b3fd2a70c69d5f7edca034be95d6e920bceae3e29ca2b3abd47bcef9d85f9161098b913391600491610a7f565b60405180910390a1565b6001600160a01b03811681146109a9575f80fd5b50565b5f602082840312156109bc575f80fd5b81356109c781610995565b9392505050565b5f80604083850312156109df575f80fd5b82356109ea81610995565b915060208301356109fa81610995565b809150509250929050565b5f60208284031215610a15575f80fd5b5035919050565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610a4357610a43610a1c565b92915050565b5f82610a6357634e487b7160e01b5f52601260045260245ffd5b500490565b8082028115828204841417610a4357610a43610a1c565b6001600160a01b0384168152608081016020808301855f5b6002811015610ab457815483529183019160019182019101610a97565b50505050826060830152949350505050565b5f60208284031215610ad6575f80fd5b81516109c781610995565b5f60208284031215610af1575f80fd5b5051919050565b80820180821115610a4357610a43610a1c56fea2646970667358221220abc80a627f269a044e21c03a16c9ae081b7d3be972c97f47cc5cee18c1e3586f64736f6c63430008170033";

    public static final String FUNC_BUY = "buy";

    public static final String FUNC_CHANGECOPYRIGHTHOLDER = "changeCopyrightHolder";

    public static final String FUNC_COPYRIGHTHOLDERS = "copyrightHolders";

    public static final String FUNC_CUMULATIVESALES = "cumulativeSales";

    public static final String FUNC_DESTROY = "destroy";

    public static final String FUNC_KECCAK256HASH = "keccak256Hash";

    public static final String FUNC_NFTCONTRACTADDRESSES = "nftContractAddresses";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_REGISTERNFTCONTRACT = "registerNftContract";

    public static final String FUNC_SETTLE = "settle";

    public static final String FUNC_SONGCID = "songCid";

    public static final Event LOGBUYERINFO_EVENT = new Event("logBuyerInfo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<StaticArray2<Bytes32>>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGRECIEVERINFO_EVENT = new Event("logRecieverInfo", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<StaticArray2<Bytes32>>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected SettlementContractExtra(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SettlementContractExtra(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SettlementContractExtra(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SettlementContractExtra(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<TransactionReceipt> changeCopyrightHolder(String prev, String _new) {
        final Function function = new Function(
                FUNC_CHANGECOPYRIGHTHOLDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, prev), 
                new org.web3j.abi.datatypes.Address(160, _new)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<String> nftContractAddresses(String param0) {
        final Function function = new Function(FUNC_NFTCONTRACTADDRESSES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<TransactionReceipt> registerNftContract(String minter) {
        final Function function = new Function(
                FUNC_REGISTERNFTCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, minter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
    public static SettlementContractExtra load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SettlementContractExtra(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SettlementContractExtra load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SettlementContractExtra(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SettlementContractExtra load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SettlementContractExtra(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SettlementContractExtra load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SettlementContractExtra(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SettlementContractExtra> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
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
        return deployRemoteCall(SettlementContractExtra.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SettlementContractExtra> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
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
        return deployRemoteCall(SettlementContractExtra.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SettlementContractExtra> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
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
        return deployRemoteCall(SettlementContractExtra.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SettlementContractExtra> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> _addresses, List<BigInteger> _proportions, List<byte[]> _songCid, BigInteger _price) {
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
        return deployRemoteCall(SettlementContractExtra.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
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
