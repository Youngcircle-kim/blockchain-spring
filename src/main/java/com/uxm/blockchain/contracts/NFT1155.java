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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class NFT1155 extends Contract {
  public static final String BINARY = "608060405234801562000010575f80fd5b50604051620022e5380380620022e58339810160408190526200003391620005aa565b60408051602081019091525f81526200004c816200013d565b50600380546001600160a01b0319166001600160a01b038316179055620000726200014f565b6200007b575f80fd5b60048054336001600160a01b03199182168117835560058054909216811790915560405163267838dd60e21b8152918201526001600160a01b038216906399e0e374906024016040805180830381865afa158015620000dc573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906200010291906200066a565b5060085560408051602081019091525f80825262000126913391906001906200021f565b600662000134838262000717565b50505062000978565b60026200014b828262000717565b5050565b60035460405163267838dd60e21b81523360048201525f9182916001600160a01b03909116906399e0e374906024016040805180830381865afa15801562000199573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190620001bf91906200066a565b5090505f8111620002175760405162461bcd60e51b815260206004820152601660248201527f4e6f74206120436f70797269676874486f6c6465722e0000000000000000000060448201526064015b60405180910390fd5b600191505090565b6001600160a01b038416620002815760405162461bcd60e51b815260206004820152602160248201527f455243313135353a206d696e7420746f20746865207a65726f206164647265736044820152607360f81b60648201526084016200020e565b335f6200028e8562000338565b90505f6200029c8562000338565b90505f868152602081815260408083206001600160a01b038b16845290915281208054879290620002cf908490620007df565b909155505060408051878152602081018790526001600160a01b03808a16925f92918716917fc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62910160405180910390a46200032f835f898989896200038c565b50505050505050565b6040805160018082528183019092526060915f91906020808301908036833701905050905082815f8151811062000373576200037362000805565b602090810291909101015292915050565b505050505050565b6001600160a01b0384163b15620003845760405163f23a6e6160e01b81526001600160a01b0385169063f23a6e6190620003d3908990899088908890889060040162000846565b6020604051808303815f875af192505050801562000410575060408051601f3d908101601f191682019092526200040d918101906200088c565b60015b620004d0576200041f620008bc565b806308c379a0036200045f575062000436620008d6565b8062000443575062000461565b8060405162461bcd60e51b81526004016200020e919062000964565b505b60405162461bcd60e51b815260206004820152603460248201527f455243313135353a207472616e7366657220746f206e6f6e2d4552433131353560448201527f526563656976657220696d706c656d656e74657200000000000000000000000060648201526084016200020e565b6001600160e01b0319811663f23a6e6160e01b146200032f5760405162461bcd60e51b815260206004820152602860248201527f455243313135353a204552433131353552656365697665722072656a656374656044820152676420746f6b656e7360c01b60648201526084016200020e565b634e487b7160e01b5f52604160045260245ffd5b601f8201601f191681016001600160401b03811182821017156200057f576200057f62000543565b6040525050565b5f5b83811015620005a257818101518382015260200162000588565b50505f910152565b5f8060408385031215620005bc575f80fd5b82516001600160401b0380821115620005d3575f80fd5b818501915085601f830112620005e7575f80fd5b815181811115620005fc57620005fc62000543565b604051915062000617601f8201601f19166020018362000557565b8082528660208285010111156200062c575f80fd5b6200063f81602084016020860162000586565b50602085015190935090506001600160a01b03811681146200065f575f80fd5b809150509250929050565b5f80604083850312156200067c575f80fd5b505080516020909101519092909150565b600181811c90821680620006a257607f821691505b602082108103620006c157634e487b7160e01b5f52602260045260245ffd5b50919050565b601f8211156200071257805f5260205f20601f840160051c81016020851015620006ee5750805b601f840160051c820191505b818110156200070f575f8155600101620006fa565b50505b505050565b81516001600160401b0381111562000733576200073362000543565b6200074b816200074484546200068d565b84620006c7565b602080601f83116001811462000781575f8415620007695750858301515b5f19600386901b1c1916600185901b17855562000384565b5f85815260208120601f198616915b82811015620007b15788860151825594840194600190910190840162000790565b5085821015620007cf57878501515f19600388901b60f8161c191681555b5050505050600190811b01905550565b80820180821115620007ff57634e487b7160e01b5f52601160045260245ffd5b92915050565b634e487b7160e01b5f52603260045260245ffd5b5f81518084526200083281602086016020860162000586565b601f01601f19169290920160200192915050565b6001600160a01b03868116825285166020820152604081018490526060810183905260a0608082018190525f90620008819083018462000819565b979650505050505050565b5f602082840312156200089d575f80fd5b81516001600160e01b031981168114620008b5575f80fd5b9392505050565b5f60033d1115620008d35760045f803e505f5160e01c5b90565b5f60443d1015620008e45790565b6040516003193d81016004833e81513d6001600160401b0380831160248401831017156200091457505050505090565b82850191508151818111156200092d5750505050505090565b843d8701016020828501011115620009485750505050505090565b620009596020828601018762000557565b509095945050505050565b602081525f620008b5602083018462000819565b61195f80620009865f395ff3fe608060405260043610610104575f3560e01c80638da5cb5b11610092578063a6f2ae3a11610062578063a6f2ae3a146102bd578063e4849b32146102c5578063e985e9c5146102e4578063ea42418b1461032b578063f242432a1461034a575f80fd5b80638da5cb5b146102565780639160e28414610275578063a035b1fe14610289578063a22cb4651461029e575f80fd5b80630e89341c116100d85780630e89341c146101b45780631aa3a008146101e05780632eb2c2d6146101f65780634e1273f4146102155780635b4246d414610241575f80fd5b8062fdd58e1461010857806301ffc9a71461013a57806307546172146101695780630b05c695146101a0575b5f80fd5b348015610113575f80fd5b50610127610122366004611122565b610369565b6040519081526020015b60405180910390f35b348015610145575f80fd5b5061015961015436600461115f565b610400565b6040519015158152602001610131565b348015610174575f80fd5b50600454610188906001600160a01b031681565b6040516001600160a01b039091168152602001610131565b3480156101ab575f80fd5b5061015961044f565b3480156101bf575f80fd5b506101d36101ce366004611181565b61050f565b60405161013191906111db565b3480156101eb575f80fd5b506101f46105a1565b005b348015610201575f80fd5b506101f4610210366004611334565b6105fb565b348015610220575f80fd5b5061023461022f3660046113d7565b610647565b60405161013191906114d6565b34801561024c575f80fd5b5061012760085481565b348015610261575f80fd5b50600554610188906001600160a01b031681565b348015610280575f80fd5b506101d3610767565b348015610294575f80fd5b5061012760075481565b3480156102a9575f80fd5b506101f46102b83660046114e8565b6107f3565b6101f4610802565b3480156102d0575f80fd5b506101f46102df366004611181565b6109ce565b3480156102ef575f80fd5b506101596102fe366004611521565b6001600160a01b039182165f90815260016020908152604080832093909416825291909152205460ff1690565b348015610336575f80fd5b50600354610188906001600160a01b031681565b348015610355575f80fd5b506101f4610364366004611552565b610a86565b5f6001600160a01b0383166103d85760405162461bcd60e51b815260206004820152602a60248201527f455243313135353a2061646472657373207a65726f206973206e6f742061207660448201526930b634b21037bbb732b960b11b60648201526084015b60405180910390fd5b505f818152602081815260408083206001600160a01b03861684529091529020545b92915050565b5f6001600160e01b03198216636cdb3d1360e11b148061043057506001600160e01b031982166303a24d0760e21b145b806103fa57506301ffc9a760e01b6001600160e01b03198316146103fa565b60035460405163267838dd60e21b81523360048201525f9182916001600160a01b03909116906399e0e374906024016040805180830381865afa158015610498573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906104bc91906115b2565b5090505f81116105075760405162461bcd60e51b81526020600482015260166024820152752737ba10309021b7b83cb934b3b43a2437b63232b91760511b60448201526064016103cf565b600191505090565b60606002805461051e906115d4565b80601f016020809104026020016040519081016040528092919081815260200182805461054a906115d4565b80156105955780601f1061056c57610100808354040283529160200191610595565b820191905f5260205f20905b81548152906001019060200180831161057857829003601f168201915b50505050509050919050565b600354604051631f31682560e11b81523360048201526001600160a01b0390911690633e62d04a906024015f604051808303815f87803b1580156105e3575f80fd5b505af11580156105f5573d5f803e3d5ffd5b50505050565b6001600160a01b038516331480610617575061061785336102fe565b6106335760405162461bcd60e51b81526004016103cf9061160c565b6106408585858585610acb565b5050505050565b606081518351146106ac5760405162461bcd60e51b815260206004820152602960248201527f455243313135353a206163636f756e747320616e6420696473206c656e677468604482015268040dad2e6dac2e8c6d60bb1b60648201526084016103cf565b5f835167ffffffffffffffff8111156106c7576106c76111ed565b6040519080825280602002602001820160405280156106f0578160200160208202803683370190505b5090505f5b845181101561075f5761073a8582815181106107135761071361165a565b602002602001015185838151811061072d5761072d61165a565b6020026020010151610369565b82828151811061074c5761074c61165a565b60209081029190910101526001016106f5565b509392505050565b60068054610774906115d4565b80601f01602080910402602001604051908101604052809291908181526020018280546107a0906115d4565b80156107eb5780601f106107c2576101008083540402835291602001916107eb565b820191905f5260205f20905b8154815290600101906020018083116107ce57829003601f168201915b505050505081565b6107fe338383610c9c565b5050565b60075434101561084d5760405162461bcd60e51b81526020600482015260166024820152753b30b63ab29034b99034b739bab33334b1b4b2b73a1760511b60448201526064016103cf565b5f610859600a34611682565b6005549091506001600160a01b03166108fc61087583346116a1565b6040518115909202915f818181858888f1935050505015801561089a573d5f803e3d5ffd5b506004546040516001600160a01b039091169082156108fc029083905f818181858888f193505050501580156108d2573d5f803e3d5ffd5b50600554604051637921219560e11b81526001600160a01b0390911660048201523360248201525f604482018190526001606483015260a0608483015260a4820152309063f242432a9060c4015f604051808303815f87803b158015610936575f80fd5b505af1158015610948573d5f803e3d5ffd5b505060035460055460405163278353d160e11b81526001600160a01b03918216600482015233602482015291169250634f06a7a291506044015f604051808303815f87803b158015610998575f80fd5b505af11580156109aa573d5f803e3d5ffd5b5050600580546001600160a01b03191633179055506109cb9050305f6107f3565b50565b5f6109d9335f610369565b11610a175760405162461bcd60e51b815260206004820152600e60248201526d6e6f74204e4654206f776e65722160901b60448201526064016103cf565b610a1f61044f565b610a27575f80fd5b5f8111610a765760405162461bcd60e51b815260206004820152601e60248201527f70726963652073686f756c64206c6172676572207468616e207a65726f21000060448201526064016103cf565b60078190556109cb3060016107f3565b6001600160a01b038516331480610aa25750610aa285336102fe565b610abe5760405162461bcd60e51b81526004016103cf9061160c565b6106408585858585610d7b565b8151835114610b2d5760405162461bcd60e51b815260206004820152602860248201527f455243313135353a2069647320616e6420616d6f756e7473206c656e677468206044820152670dad2e6dac2e8c6d60c31b60648201526084016103cf565b6001600160a01b038416610b535760405162461bcd60e51b81526004016103cf906116b4565b335f5b8451811015610c2e575f858281518110610b7257610b7261165a565b602002602001015190505f858381518110610b8f57610b8f61165a565b6020908102919091018101515f84815280835260408082206001600160a01b038e168352909352919091205490915081811015610bde5760405162461bcd60e51b81526004016103cf906116f9565b5f838152602081815260408083206001600160a01b038e8116855292528083208585039055908b16825281208054849290610c1a908490611743565b909155505060019093019250610b56915050565b50846001600160a01b0316866001600160a01b0316826001600160a01b03167f4a39dc06d4c0dbc64b70af90fd698a233a518aa5d07e595d983b8c0526c8f7fb8787604051610c7e929190611756565b60405180910390a4610c94818787878787610ea1565b505050505050565b816001600160a01b0316836001600160a01b031603610d0f5760405162461bcd60e51b815260206004820152602960248201527f455243313135353a2073657474696e6720617070726f76616c20737461747573604482015268103337b91039b2b63360b91b60648201526084016103cf565b6001600160a01b038381165f81815260016020908152604080832094871680845294825291829020805460ff191686151590811790915591519182527f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31910160405180910390a3505050565b6001600160a01b038416610da15760405162461bcd60e51b81526004016103cf906116b4565b335f610dac85611004565b90505f610db885611004565b90505f868152602081815260408083206001600160a01b038c16845290915290205485811015610dfa5760405162461bcd60e51b81526004016103cf906116f9565b5f878152602081815260408083206001600160a01b038d8116855292528083208985039055908a16825281208054889290610e36908490611743565b909155505060408051888152602081018890526001600160a01b03808b16928c821692918816917fc3d58168c5ae7397731d063d5bbf3d657854427343f4c083240f7aacaa2d0f62910160405180910390a4610e96848a8a8a8a8a61104d565b505050505050505050565b6001600160a01b0384163b15610c945760405163bc197c8160e01b81526001600160a01b0385169063bc197c8190610ee59089908990889088908890600401611783565b6020604051808303815f875af1925050508015610f1f575060408051601f3d908101601f19168201909252610f1c918101906117e0565b60015b610fcb57610f2b6117fb565b806308c379a003610f645750610f3f611814565b80610f4a5750610f66565b8060405162461bcd60e51b81526004016103cf91906111db565b505b60405162461bcd60e51b815260206004820152603460248201527f455243313135353a207472616e7366657220746f206e6f6e2d455243313135356044820152732932b1b2b4bb32b91034b6b83632b6b2b73a32b960611b60648201526084016103cf565b6001600160e01b0319811663bc197c8160e01b14610ffb5760405162461bcd60e51b81526004016103cf9061189d565b50505050505050565b6040805160018082528183019092526060915f91906020808301908036833701905050905082815f8151811061103c5761103c61165a565b602090810291909101015292915050565b6001600160a01b0384163b15610c945760405163f23a6e6160e01b81526001600160a01b0385169063f23a6e619061109190899089908890889088906004016118e5565b6020604051808303815f875af19250505080156110cb575060408051601f3d908101601f191682019092526110c8918101906117e0565b60015b6110d757610f2b6117fb565b6001600160e01b0319811663f23a6e6160e01b14610ffb5760405162461bcd60e51b81526004016103cf9061189d565b80356001600160a01b038116811461111d575f80fd5b919050565b5f8060408385031215611133575f80fd5b61113c83611107565b946020939093013593505050565b6001600160e01b0319811681146109cb575f80fd5b5f6020828403121561116f575f80fd5b813561117a8161114a565b9392505050565b5f60208284031215611191575f80fd5b5035919050565b5f81518084525f5b818110156111bc576020818501810151868301820152016111a0565b505f602082860101526020601f19601f83011685010191505092915050565b602081525f61117a6020830184611198565b634e487b7160e01b5f52604160045260245ffd5b601f8201601f1916810167ffffffffffffffff81118282101715611227576112276111ed565b6040525050565b5f67ffffffffffffffff821115611247576112476111ed565b5060051b60200190565b5f82601f830112611260575f80fd5b8135602061126d8261122e565b60405161127a8282611201565b80915083815260208101915060208460051b87010193508684111561129d575f80fd5b602086015b848110156112b957803583529183019183016112a2565b509695505050505050565b5f82601f8301126112d3575f80fd5b813567ffffffffffffffff8111156112ed576112ed6111ed565b604051611304601f8301601f191660200182611201565b818152846020838601011115611318575f80fd5b816020850160208301375f918101602001919091529392505050565b5f805f805f60a08688031215611348575f80fd5b61135186611107565b945061135f60208701611107565b9350604086013567ffffffffffffffff8082111561137b575f80fd5b61138789838a01611251565b9450606088013591508082111561139c575f80fd5b6113a889838a01611251565b935060808801359150808211156113bd575f80fd5b506113ca888289016112c4565b9150509295509295909350565b5f80604083850312156113e8575f80fd5b823567ffffffffffffffff808211156113ff575f80fd5b818501915085601f830112611412575f80fd5b8135602061141f8261122e565b60405161142c8282611201565b83815260059390931b850182019282810191508984111561144b575f80fd5b948201945b838610156114705761146186611107565b82529482019490820190611450565b96505086013592505080821115611485575f80fd5b5061149285828601611251565b9150509250929050565b5f815180845260208085019450602084015f5b838110156114cb578151875295820195908201906001016114af565b509495945050505050565b602081525f61117a602083018461149c565b5f80604083850312156114f9575f80fd5b61150283611107565b915060208301358015158114611516575f80fd5b809150509250929050565b5f8060408385031215611532575f80fd5b61153b83611107565b915061154960208401611107565b90509250929050565b5f805f805f60a08688031215611566575f80fd5b61156f86611107565b945061157d60208701611107565b93506040860135925060608601359150608086013567ffffffffffffffff8111156115a6575f80fd5b6113ca888289016112c4565b5f80604083850312156115c3575f80fd5b505080516020909101519092909150565b600181811c908216806115e857607f821691505b60208210810361160657634e487b7160e01b5f52602260045260245ffd5b50919050565b6020808252602e908201527f455243313135353a2063616c6c6572206973206e6f7420746f6b656e206f776e60408201526d195c881bdc88185c1c1c9bdd995960921b606082015260800190565b634e487b7160e01b5f52603260045260245ffd5b634e487b7160e01b5f52601160045260245ffd5b5f8261169c57634e487b7160e01b5f52601260045260245ffd5b500490565b818103818111156103fa576103fa61166e565b60208082526025908201527f455243313135353a207472616e7366657220746f20746865207a65726f206164604082015264647265737360d81b606082015260800190565b6020808252602a908201527f455243313135353a20696e73756666696369656e742062616c616e636520666f60408201526939103a3930b739b332b960b11b606082015260800190565b808201808211156103fa576103fa61166e565b604081525f611768604083018561149c565b828103602084015261177a818561149c565b95945050505050565b6001600160a01b0386811682528516602082015260a0604082018190525f906117ae9083018661149c565b82810360608401526117c0818661149c565b905082810360808401526117d48185611198565b98975050505050505050565b5f602082840312156117f0575f80fd5b815161117a8161114a565b5f60033d11156118115760045f803e505f5160e01c5b90565b5f60443d10156118215790565b6040516003193d81016004833e81513d67ffffffffffffffff816024840111818411171561185157505050505090565b82850191508151818111156118695750505050505090565b843d87010160208285010111156118835750505050505090565b61189260208286010187611201565b509095945050505050565b60208082526028908201527f455243313135353a204552433131353552656365697665722072656a656374656040820152676420746f6b656e7360c01b606082015260800190565b6001600160a01b03868116825285166020820152604081018490526060810183905260a0608082018190525f9061191e90830184611198565b97965050505050505056fea264697066735822122050efedfbae8de22be35161106f5efdd22d7b716448263ec067fc1e87d45459bf64736f6c63430008170033";

  public static final String FUNC_BALANCEOF = "balanceOf";

  public static final String FUNC_BALANCEOFBATCH = "balanceOfBatch";

  public static final String FUNC_BUY = "buy";

  public static final String FUNC_DIR = "dir";

  public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

  public static final String FUNC_ISCOPYRIGHTHOLDER = "isCopyrightHolder";

  public static final String FUNC_MINTER = "minter";

  public static final String FUNC_OWNER = "owner";

  public static final String FUNC_PRICE = "price";

  public static final String FUNC_PROPORTION = "proportion";

  public static final String FUNC_REGISTER = "register";

  public static final String FUNC_SAFEBATCHTRANSFERFROM = "safeBatchTransferFrom";

  public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

  public static final String FUNC_SELL = "sell";

  public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

  public static final String FUNC_SETTLEMENTCONTRACT = "settlementContract";

  public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

  public static final String FUNC_URI = "uri";

  public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
  ;

  public static final Event TRANSFERBATCH_EVENT = new Event("TransferBatch",
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<DynamicArray<Uint256>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
  ;

  public static final Event TRANSFERSINGLE_EVENT = new Event("TransferSingle",
      Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
  ;

  public static final Event URI_EVENT = new Event("URI",
      Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>(true) {}));
  ;

  @Deprecated
  protected NFT1155(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  protected NFT1155(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
  }

  @Deprecated
  protected NFT1155(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  protected NFT1155(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
    ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static ApprovalForAllEventResponse getApprovalForAllEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVALFORALL_EVENT, log);
    ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
    typedResponse.log = log;
    typedResponse.account = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getApprovalForAllEventFromLog(log));
  }

  public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
    return approvalForAllEventFlowable(filter);
  }

  public static List<TransferBatchEventResponse> getTransferBatchEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFERBATCH_EVENT, transactionReceipt);
    ArrayList<TransferBatchEventResponse> responses = new ArrayList<TransferBatchEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
      typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
      typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TransferBatchEventResponse getTransferBatchEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFERBATCH_EVENT, log);
    TransferBatchEventResponse typedResponse = new TransferBatchEventResponse();
    typedResponse.log = log;
    typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
    typedResponse.ids = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
    typedResponse.values = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(1)).getNativeValueCopy();
    return typedResponse;
  }

  public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getTransferBatchEventFromLog(log));
  }

  public Flowable<TransferBatchEventResponse> transferBatchEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TRANSFERBATCH_EVENT));
    return transferBatchEventFlowable(filter);
  }

  public static List<TransferSingleEventResponse> getTransferSingleEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFERSINGLE_EVENT, transactionReceipt);
    ArrayList<TransferSingleEventResponse> responses = new ArrayList<TransferSingleEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
      typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
      typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
      typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static TransferSingleEventResponse getTransferSingleEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFERSINGLE_EVENT, log);
    TransferSingleEventResponse typedResponse = new TransferSingleEventResponse();
    typedResponse.log = log;
    typedResponse.operator = (String) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
    typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
    typedResponse.id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
    typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
    return typedResponse;
  }

  public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getTransferSingleEventFromLog(log));
  }

  public Flowable<TransferSingleEventResponse> transferSingleEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(TRANSFERSINGLE_EVENT));
    return transferSingleEventFlowable(filter);
  }

  public static List<URIEventResponse> getURIEvents(TransactionReceipt transactionReceipt) {
    List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(URI_EVENT, transactionReceipt);
    ArrayList<URIEventResponse> responses = new ArrayList<URIEventResponse>(valueList.size());
    for (Contract.EventValuesWithLog eventValues : valueList) {
      URIEventResponse typedResponse = new URIEventResponse();
      typedResponse.log = eventValues.getLog();
      typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
      typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
      responses.add(typedResponse);
    }
    return responses;
  }

  public static URIEventResponse getURIEventFromLog(Log log) {
    Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(URI_EVENT, log);
    URIEventResponse typedResponse = new URIEventResponse();
    typedResponse.log = log;
    typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
    typedResponse.value = (String) eventValues.getNonIndexedValues().get(0).getValue();
    return typedResponse;
  }

  public Flowable<URIEventResponse> uRIEventFlowable(EthFilter filter) {
    return web3j.ethLogFlowable(filter).map(log -> getURIEventFromLog(log));
  }

  public Flowable<URIEventResponse> uRIEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
    EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
    filter.addSingleTopic(EventEncoder.encode(URI_EVENT));
    return uRIEventFlowable(filter);
  }

  public RemoteFunctionCall<BigInteger> balanceOf(String account, BigInteger id) {
    final Function function = new Function(FUNC_BALANCEOF,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account),
            new org.web3j.abi.datatypes.generated.Uint256(id)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<List> balanceOfBatch(List<String> accounts, List<BigInteger> ids) {
    final Function function = new Function(FUNC_BALANCEOFBATCH,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                org.web3j.abi.datatypes.Address.class,
                org.web3j.abi.Utils.typeMap(accounts, org.web3j.abi.datatypes.Address.class)),
            new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                org.web3j.abi.datatypes.generated.Uint256.class,
                org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class))),
        Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
    return new RemoteFunctionCall<List>(function,
        new Callable<List>() {
          @Override
          @SuppressWarnings("unchecked")
          public List call() throws Exception {
            List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
            return convertToNative(result);
          }
        });
  }

  public RemoteFunctionCall<TransactionReceipt> buy(BigInteger weiValue) {
    final Function function = new Function(
        FUNC_BUY,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function, weiValue);
  }

  public RemoteFunctionCall<String> dir() {
    final Function function = new Function(FUNC_DIR,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<Boolean> isApprovedForAll(String account, String operator) {
    final Function function = new Function(FUNC_ISAPPROVEDFORALL,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account),
            new org.web3j.abi.datatypes.Address(160, operator)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<Boolean> isCopyrightHolder() {
    final Function function = new Function(FUNC_ISCOPYRIGHTHOLDER,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<String> minter() {
    final Function function = new Function(FUNC_MINTER,
        Arrays.<Type>asList(),
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

  public RemoteFunctionCall<BigInteger> proportion() {
    final Function function = new Function(FUNC_PROPORTION,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    return executeRemoteCallSingleValueReturn(function, BigInteger.class);
  }

  public RemoteFunctionCall<TransactionReceipt> register() {
    final Function function = new Function(
        FUNC_REGISTER,
        Arrays.<Type>asList(),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> safeBatchTransferFrom(String from, String to, List<BigInteger> ids, List<BigInteger> amounts, byte[] data) {
    final Function function = new Function(
        FUNC_SAFEBATCHTRANSFERFROM,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
            new org.web3j.abi.datatypes.Address(160, to),
            new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                org.web3j.abi.datatypes.generated.Uint256.class,
                org.web3j.abi.Utils.typeMap(ids, org.web3j.abi.datatypes.generated.Uint256.class)),
            new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                org.web3j.abi.datatypes.generated.Uint256.class,
                org.web3j.abi.Utils.typeMap(amounts, org.web3j.abi.datatypes.generated.Uint256.class)),
            new org.web3j.abi.datatypes.DynamicBytes(data)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger id, BigInteger amount, byte[] data) {
    final Function function = new Function(
        FUNC_SAFETRANSFERFROM,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from),
            new org.web3j.abi.datatypes.Address(160, to),
            new org.web3j.abi.datatypes.generated.Uint256(id),
            new org.web3j.abi.datatypes.generated.Uint256(amount),
            new org.web3j.abi.datatypes.DynamicBytes(data)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> sell(BigInteger _price) {
    final Function function = new Function(
        FUNC_SELL,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_price)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<TransactionReceipt> setApprovalForAll(String operator, Boolean approved) {
    final Function function = new Function(
        FUNC_SETAPPROVALFORALL,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, operator),
            new org.web3j.abi.datatypes.Bool(approved)),
        Collections.<TypeReference<?>>emptyList());
    return executeRemoteCallTransaction(function);
  }

  public RemoteFunctionCall<String> settlementContract() {
    final Function function = new Function(FUNC_SETTLEMENTCONTRACT,
        Arrays.<Type>asList(),
        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
    final Function function = new Function(FUNC_SUPPORTSINTERFACE,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    return executeRemoteCallSingleValueReturn(function, Boolean.class);
  }

  public RemoteFunctionCall<String> uri(BigInteger param0) {
    final Function function = new Function(FUNC_URI,
        Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    return executeRemoteCallSingleValueReturn(function, String.class);
  }

  @Deprecated
  public static NFT1155 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
    return new NFT1155(contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  @Deprecated
  public static NFT1155 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
    return new NFT1155(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
  }

  public static NFT1155 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
    return new NFT1155(contractAddress, web3j, credentials, contractGasProvider);
  }

  public static NFT1155 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
    return new NFT1155(contractAddress, web3j, transactionManager, contractGasProvider);
  }

  public static RemoteCall<NFT1155> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _dir, String _contract) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_dir),
        new org.web3j.abi.datatypes.Address(160, _contract)));
    return deployRemoteCall(NFT1155.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
  }

  public static RemoteCall<NFT1155> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _dir, String _contract) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_dir),
        new org.web3j.abi.datatypes.Address(160, _contract)));
    return deployRemoteCall(NFT1155.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<NFT1155> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _dir, String _contract) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_dir),
        new org.web3j.abi.datatypes.Address(160, _contract)));
    return deployRemoteCall(NFT1155.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
  }

  @Deprecated
  public static RemoteCall<NFT1155> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _dir, String _contract) {
    String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_dir),
        new org.web3j.abi.datatypes.Address(160, _contract)));
    return deployRemoteCall(NFT1155.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
  }

  public static class ApprovalForAllEventResponse extends BaseEventResponse {
    public String account;

    public String operator;

    public Boolean approved;
  }

  public static class TransferBatchEventResponse extends BaseEventResponse {
    public String operator;

    public String from;

    public String to;

    public List<BigInteger> ids;

    public List<BigInteger> values;
  }

  public static class TransferSingleEventResponse extends BaseEventResponse {
    public String operator;

    public String from;

    public String to;

    public BigInteger id;

    public BigInteger value;
  }

  public static class URIEventResponse extends BaseEventResponse {
    public BigInteger id;

    public String value;
  }
}
