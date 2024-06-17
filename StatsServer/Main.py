from flask import Flask, request, jsonify

app = Flask(__name__)


@app.route('/stats', methods=['POST'])
def process_request():
    data = request.get_json()

    dates = list(data["map"].keys())
    visits = list(data["map"].values())
    likes = data["countLikes"]
    posts = data["countPosts"]

    total_visits = sum(visits)
    average_visits = total_visits / len(dates)
    if posts != 0:
        average_likes = likes / posts
    else:
        average_likes = 0
    max_visits = max(visits)
    index_max_visits = visits.index(max_visits)
    date_most_visits = dates[index_max_visits]

    response = {
        'visit_data': data["map"],
        'avg_visit': average_visits,
        'avg_likes': average_likes,
        'all_posts': posts,
        'all_likes': likes,
        'first_date': min(dates),
        'popular_day': date_most_visits
    }
    return jsonify(response)


if __name__ == '__main__':
    app.run(debug=True, port=8099)
