# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc

import audio_pb2 as audio__pb2


class AudioServiceStub(object):
    """Nombre del servicio: "AudioService"
    La función "downloadAudio()" toma la entrada de tipo DownloadFileRequest y devuelve la salida de tipo DataChunkResponse
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.downloadAudio = channel.unary_stream(
                '/AudioService/downloadAudio',
                request_serializer=audio__pb2.DownloadFileRequest.SerializeToString,
                response_deserializer=audio__pb2.DataChunkResponse.FromString,
                )


class AudioServiceServicer(object):
    """Nombre del servicio: "AudioService"
    La función "downloadAudio()" toma la entrada de tipo DownloadFileRequest y devuelve la salida de tipo DataChunkResponse
    """

    def downloadAudio(self, request, context):
        """Función a implementar
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_AudioServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'downloadAudio': grpc.unary_stream_rpc_method_handler(
                    servicer.downloadAudio,
                    request_deserializer=audio__pb2.DownloadFileRequest.FromString,
                    response_serializer=audio__pb2.DataChunkResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'AudioService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


 # This class is part of an EXPERIMENTAL API.
class AudioService(object):
    """Nombre del servicio: "AudioService"
    La función "downloadAudio()" toma la entrada de tipo DownloadFileRequest y devuelve la salida de tipo DataChunkResponse
    """

    @staticmethod
    def downloadAudio(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_stream(request, target, '/AudioService/downloadAudio',
            audio__pb2.DownloadFileRequest.SerializeToString,
            audio__pb2.DataChunkResponse.FromString,
            options, channel_credentials,
            insecure, call_credentials, compression, wait_for_ready, timeout, metadata)
