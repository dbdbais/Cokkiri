package com.ssafy.iscode.regular.controller;

import com.ssafy.iscode.regular.model.dto.RegularRequestDto;
import com.ssafy.iscode.regular.model.dto.RegularResponseDto;
import com.ssafy.iscode.regular.service.RegularService;
import com.ssafy.iscode.websocket.handler.LobbyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/regular")
public class RegularController {

    @Autowired
    private RegularService regularService;

    @Autowired
    private LobbyWebSocketHandler lobbyWebSocketHandler;

    @PostMapping("/create-regular")
    public ResponseEntity<Long> createRegular(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            Long sessionId = regularService.createRegular(regularRequestDto);
            return new ResponseEntity<>(sessionId, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-regular")
    public ResponseEntity<RegularResponseDto> updateRegular(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            int result = regularService.updateRegular(regularRequestDto);

            if(result == 1) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/accept-join")
    public ResponseEntity<RegularResponseDto> acceptJoin(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            int result = regularService.enterRegular(regularRequestDto);

            if(result == 1) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-join")
    public ResponseEntity<RegularResponseDto> sendJoin(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            int result = regularService.joinRegular(regularRequestDto);

            if(result == 1) {
                try {
                    String event = ".|!|.|!|REGULAR|!|.";
                    String hostName = regularService.getRegular(regularRequestDto.getSessionId()).getHostName();
                    lobbyWebSocketHandler.sendEvent(hostName, event);

                    return new ResponseEntity<>(HttpStatus.OK);
                } catch (RuntimeException e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-request")
    public ResponseEntity<List<RegularResponseDto>> getRequest(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            List<RegularResponseDto> responseDtos = regularService.getRequest(regularRequestDto);

            return new ResponseEntity<>(responseDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/quit-regular")
    public ResponseEntity<RegularResponseDto> quitRegular(@RequestBody RegularRequestDto regularRequestDto) {
        try {
            int result = regularService.quitRegular(regularRequestDto);

            if(result == 1) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<RegularResponseDto> getRegular(@PathVariable("sessionId") Long sessionId) {
        try {
            RegularResponseDto regularResponseDto = regularService.getRegular(sessionId);
            return new ResponseEntity<>(regularResponseDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<RegularResponseDto>> getRegulars(@RequestParam Map<String, String> params) {
        String regularName = params.get("regularName");
        String weekday = params.get("weekday");
        Integer start = params.get("start") == null ? null : Integer.parseInt(params.get("start"));
        Integer end = params.get("end") == null ? null : Integer.parseInt(params.get("end"));
        int page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));

        return new ResponseEntity<>(regularService.getRegulars(regularName, weekday, start, end, page), HttpStatus.OK);
    }
}
