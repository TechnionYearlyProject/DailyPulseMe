function heartStats(type, rate, age){
  if(type = "rest"){
    return restheartStats(rate, age);
  }
  return sportHeartStats(rate, age);
}
function restHeartStats(rate, age){
  if(age <= 35){
    if(rate <= 45){
      return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
    }
    if(rate <= 61){
      return "Normal activity, heart rate indicates you're in excellent shape."
    }
    if(rate <= 70){
      return "Normal activity, heart rate indicates you're in above average shape."
    }
    if(rate <= 75){
      return "Normal activity, heart rate indicates you're in average shape."
    }
    if(rate <= 81){
      return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
    }
    return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
  }
  if(age >= 36 && age <= 55){
    if(rate <= 49){
      return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
    }
    if(rate <= 63){
      return "Normal activity, heart rate indicates you're in excellent shape."
    }
    if(rate <= 72){
      return "Normal activity, heart rate indicates you're in above average shape."
    }
    if(rate <= 77){
      return "Normal activity, heart rate indicates you're in average shape."
    }
    if(rate <= 83){
      return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
    }
    return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
  }
  if(age >= 56){
    if(rate <= 50){
      return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
    }
    if(rate <= 66){
      return "Normal activity, heart rate indicates you're in excellent shape."
    }
    if(rate <= 74){
      return "Normal activity, heart rate indicates you're in above average shape."
    }
    if(rate <= 78){
      return "Normal activity, heart rate indicates you're in average shape."
    }
    if(rate <= 84){
      return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
    }
    return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
  }
}
function sportHeartStats(rate, age){
  maxRate = 220 - age;
  if(rate < 0.5*maxRate){
    return "Heart rate is too low, we recommend you to be more active in this session."
  }
  if(rate >=0.5*maxRate && rate < 0.6*maxRate){
    return "This activity is great for low intensity sports and is recommended to do often to generally stay in shape."
  }
  if(rate >=0.6*maxRate && rate < 0.7*maxRate){
    return "This activity is great for for weight loss and calorie burn, ideal for burning fat with preserving as much muscle mass as possible."
  }
  if(rate >=0.7*maxRate && rate < 0.8*maxRate){
    return "This activity is great improving aerobic and cardio fitness, ideal for increasing endurance over long distances."
  }
  if(rate >=0.8*maxRate && rate < 0.9*maxRate){
    return "This activity is great improving anaerobic fitness and muscle strength, ideal if you are trying to build muscle."
  }
  return "This activity is great improving maximum performance and speed, ideal for short bursts of intense activity and shouldn't be done over a long period of time."
}
